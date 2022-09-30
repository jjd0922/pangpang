package com.newper.entity;

import com.newper.constant.OState;
import com.newper.constant.PayState;
import com.newper.constant.PhType;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payIdx;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PayState payState = PayState.BEFORE;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PayState payCancelState = PayState.BEFORE;
    /** 결제 금액 payProductPrice + payDelivery*/
    private int payPrice;
    /** 상품 금액*/
    private int payProductPrice;
    private int payDelivery;
    /** FK IAMPORT_METHOD.IPM_IDX */
    private Integer payIpmIdx;


    private int payMileage;
    /** 적립 완료 여부*/
    private boolean payMileageFlag;
    @Builder.Default
    private Map<String,Object> payJson = new HashMap<>();

    /** OneToOne */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "payment",cascade = CascadeType.ALL)
    private List<Orders> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "payment",cascade = CascadeType.ALL)
    @OrderBy(value = "phFlag desc, phIdx desc")
    private List<PaymentHistory> paymentHistoryList;

    @PrePersist
    @PreUpdate
    public void paymentSave(){

        if (getPayState() == null) {
            throw new MsgException("결제상태를 선택해주세요.");
        }
//        if (getPayMethod() == null) {
//            throw new MsgException("결제방식을 선택해주세요.");
//        }

    }

    /** order에서 setPayment로 세팅할 때 호출되는 메서드*/
    void setOrders(Orders orders){
        this.orders = new ArrayList<>();
        this.orders.add(orders);
    }
    public Orders getOrders(){
        return this.orders.get(0);
    }

    /** 결제 요청 가능한지 체크 후 return paymentHistory*/
    public PaymentHistory createPayReq(){
        if(getPayState() != PayState.BEFORE){
            throw new MsgException("다시 시도 부탁드립니다");
        }

        List<PaymentHistory> paymentHistoryList = getPaymentHistoryList();
        if (paymentHistoryList == null) {
            paymentHistoryList = new ArrayList<>();
            setPaymentHistoryList(paymentHistoryList);
        }

        findFlag : for (PaymentHistory paymentHistory : paymentHistoryList) {
            if (paymentHistory.isPhFlag() && paymentHistory.getPhType() == PhType.PAY) {
                //마지막 결제 요청 check
                paymentHistory.setPhFlag(false);
            }
        }

        PaymentHistory paymentHistory = PaymentHistory.builder()
                .payment(this)
                .phType(PhType.PAY)
                .phReq("")
                .phRes("")
                .phFlag(true)
                .build();
        paymentHistoryList.add(paymentHistory);

        setPayState(PayState.REQ);

        return  paymentHistory;
    }
    /** 결제 가격 계산*/
    public void calculatePrice(){
        List<OrderGs> orderGs = getOrders().getOrderGs();
        int ogPrice = 0;
        int ogPoint = 0;
        int ogMileage = 0;
        int ogCoupon = 0;
        for (OrderGs ogs : orderGs) {
            ogPrice += ogs.getOgPrice();
            ogPoint += ogs.getOgPoint();
            ogMileage += ogs.getOgMileage();
            ogCoupon += ogs.getOgCoupon();
        }
        setPayProductPrice(ogPrice - ogPoint - ogMileage - ogCoupon);
        setPayPrice(payProductPrice + payDelivery);
    }
    /** 결제 성공*/
    public void paySuccess(){
        setPayState(PayState.SUCCESS);
        Orders orders = getOrders();
        if( orders.getOState() == OState.BEFORE){
            orders.setOState(OState.DONE);
        }
    }
    /** 결제 취소 요청 가능한지 체크 후 return paymentHistory*/
    public PaymentHistory createPayCancelReq(){
        List<PaymentHistory> paymentHistoryList = getPaymentHistoryList();
        if (paymentHistoryList == null) {
            paymentHistoryList = new ArrayList<>();
            setPaymentHistoryList(paymentHistoryList);
        }

        findFlag : for (PaymentHistory paymentHistory : paymentHistoryList) {
            if (paymentHistory.isPhFlag() && paymentHistory.getPhType() == PhType.CANCEL) {
                //마지막 결제 요청 check
                paymentHistory.setPhFlag(false);

//                    paymentHistory.getPhCode();

                break findFlag;
            }
        }

        PaymentHistory paymentHistory = PaymentHistory.builder()
                .payment(this)
                .phType(PhType.CANCEL)
                .phReq("")
                .phRes("")
                .phFlag(true)
                .build();
        paymentHistoryList.add(paymentHistory);

        return  paymentHistory;
    }
    /** 마지막 결제요청 ph 가져오기*/
    public PaymentHistory getLastPaymentHistory(PhType phType){
        for (PaymentHistory ph : getPaymentHistoryList()) {
            if (ph.isPhFlag() && ph.getPhType() == phType) {
                return ph;
            }
        }
        return null;
    }
}

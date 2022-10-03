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

    /** 결제 상태 표기 시에는 getPayStateString() method 사용*/
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PayState payState = PayState.BEFORE;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PayState payCancelState = PayState.BEFORE;
    /** 결제 금액 payProductPrice + payDelivery*/
    private int payTotal;
    /** 할인 전 상품 금액*/
    private int payPrice;
    /** 할인 전 배송비*/
    private int payDelivery;
    /** 사용 예치금*/
    private int payPoint;
    /** 사용 마일리지*/
    private int payMileage;
    /** 사용 쿠폰 할인 상품금액*/
    private int payCouponPrice;
    /** 사용 쿠폰 할인 배송비*/
    private int payCouponDelivery;
    /** FK IAMPORT_METHOD.IPM_IDX */
    private Integer payIpmIdx;

    /** 적립 마일리지*/
    private int payPlusMileage;
    /** 적립 완료 여부*/
    private boolean payMileageFlag;
    /**<pre>
     * pay_method : 결제수단. iamport_method.ipm_name
     * vbank_code : 가상계좌 은행코드
     * vbank_name : 가상계좌 은행명
     * vbank_num : 가상계좌 계좌번호
     * vbank_holder : 가상계좌 예금주
     * </pre>*/
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
    /** 결제 금액 계산*/
    public void calculatePrice(){
        List<OrderGs> orderGs = getOrders().getOrderGs();
        payPrice = 0;
        payDelivery = 0;
        payPoint = 0;
        payMileage = 0;
        payCouponPrice = 0;
        payCouponDelivery = 0;

        for (OrderGs ogs : orderGs) {
            payPrice  += ogs.getOgPrice();
            payDelivery += ogs.getOgDelivery();
            payPoint += ogs.getOgPoint();
            payMileage += ogs.getOgMileage();
            payCouponPrice += ogs.getOgCouponPrice();
            payCouponDelivery += ogs.getOgCouponDelivery();
        }

        setPayTotal(payPrice + payDelivery - payPoint -payMileage - payCouponPrice - payCouponDelivery);
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

    /** PAY_JSON에 세팅 */
    public void putPayJson(String key, Object value) {
        getPayJson().put(key, value);
    }
    /** PAY_JSON에 값 가져오기 */
    public Object getPayJsonValue(String key) {
        return getPayJson().get(key);
    }
    /** PAY_JSON containsKey*/
    public boolean payJsonContains(String key){
        return getPayJson().containsKey(key);
    }
    /** 가상계좌 정보 문자열*/
    public String vbankInfo(){
        if (getPayJson().containsKey("vbank_name")) {
            return getPayJson().get("vbank_name")+" "+payJson.get("vbank_num")+" 예금주: "+payJson.get("vbank_holder");
        }else{
            return "";
        }
    }
    /** 결제 상태 문자열*/
    public String getPayStateString(){
        PayState payState = getPayState();
        if (payState == PayState.WAIT) {
            return getPayState().getOption();
        }else{
            return "결제 "+getPayState().getOption();
        }
    }
    /** 할인 금액 가져오기. 0 = 할인전 상품금액 , 1 = 배송비 , 2 = 할인 */
    public int discountAmount(){
        return getPayPoint() + payMileage + payCouponPrice + payCouponDelivery;
    }

}

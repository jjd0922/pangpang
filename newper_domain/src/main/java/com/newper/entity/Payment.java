package com.newper.entity;

import com.newper.constant.PayState;
import com.newper.constant.PhType;
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
    /** 결제 금액*/
    private int payPrice;
    private int payProductPrice;
    private int payDelivery;

    private String payMethod;

    private int payMileage;
    @Builder.Default
    private Map<String,Object> payJson = new HashMap<>();

    /** OneToOne */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "payment",cascade = CascadeType.ALL)
    private List<Orders> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "payment",cascade = CascadeType.ALL)
    @OrderBy(value = "phFlag desc, phIdx desc")
    private List<PaymentHistory> paymentHistoryList;

    /** order에서 setPayment로 세팅할 때 호출되는 메서드*/
    void setOrders(Orders orders){
        this.orders = new ArrayList<>();
        this.orders.add(orders);
    }

    /** 결제 요청 가능한지 체크 후 return paymentHistory*/
    public PaymentHistory createPayReq(){
        List<PaymentHistory> paymentHistoryList = getPaymentHistoryList();
        if (paymentHistoryList == null) {
            paymentHistoryList = new ArrayList<>();
            setPaymentHistoryList(paymentHistoryList);
        }

        findFlag : for (PaymentHistory paymentHistory : paymentHistoryList) {
            if (paymentHistory.isPhFlag()) {
                //마지막 결제 요청 check
                paymentHistory.setPhFlag(false);

//                    paymentHistory.getPhCode();

                break findFlag;
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

        return  paymentHistory;
    }
}

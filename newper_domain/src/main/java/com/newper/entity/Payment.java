package com.newper.entity;

import com.newper.constant.PayState;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
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
    private int payPrice;
    private int payProductPrice;
    private int payDelivery;

    private String payMethod;

    private int payMileage;
    private Map<String,Object> payJson;

    /** OneToOne */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "payment",cascade = CascadeType.ALL)
    private List<Orders> orders;

    /** order에서 setPayment로 세팅할 때 호출되는 메서드*/
    void setOrders(Orders orders){
        this.orders = new ArrayList<>();
        this.orders.add(orders);
    }
}

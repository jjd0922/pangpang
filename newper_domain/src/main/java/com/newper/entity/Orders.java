package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "O_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "O_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "O_PAY_IDX", referencedColumnName = "payIdx")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "O_AD_IDX", referencedColumnName = "adIdx")
    private OrderAddress orderAddress;

    private String oLocation;

    private String oCode;

    private String oState;

    private String oDeliveryState;

    private String oCancelState;

    private String oName;

    private String oPhone;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate oDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalTime oTime;

    private String oMemo;

    public void setPayment(Payment payment){
        System.out.println("pay!!!");
        this.payment = payment;
        payment.setOrders(this);
    }
}

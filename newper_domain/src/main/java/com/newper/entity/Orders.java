package com.newper.entity;

import com.newper.constant.OCancelState;
import com.newper.constant.ODeliveryState;
import com.newper.constant.OLocation;
import com.newper.constant.OState;
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

    @Enumerated(EnumType.STRING)
    private OLocation oLocation;

    private String oCode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OState oState = OState.BEFORE;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ODeliveryState oDeliveryState = ODeliveryState.BEFORE;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OCancelState oCancelState = OCancelState.NONE;

    private String oName;

    private String oPhone;

    @Column(updatable = false)
    private LocalDate oDate;
    @Column(updatable = false)
    private LocalTime oTime;

    private String oMemo;

    public void setPayment(Payment payment){
        System.out.println("pay!!!");
        this.payment = payment;
        payment.setOrders(this);
    }

}

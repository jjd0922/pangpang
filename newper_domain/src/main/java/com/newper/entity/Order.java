package com.newper.entity;

import com.newper.constant.OLocation;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OIdx;

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

    private String oState;

    private String oDeliveryState;

    private String oCancelState;

    private String oName;

    private String oPhone;

    private LocalDate oDate;

    private LocalTime oTime;

    private String oMemo;

    @OneToMany(mappedBy = "ogIdx")
    private List<OrderGs> orderGs;

}

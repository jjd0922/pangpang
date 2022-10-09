package com.newper.entity;

import com.newper.constant.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderGs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ogIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_SPO_IDX", referencedColumnName = "spoIdx")
    private ShopProductOption shopProductOption;

    /** 결합 상품 그룹*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_OGG_IDX", referencedColumnName = "oggIdx")
    private OrderGsGroup orderGsGroup;

    /** 반품 요청*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_OGGC_IDX", referencedColumnName = "oggcIdx")
    private OrderGsGroupCancel orderGsGroupCancel;

    /** 교환으로 나간 주문 구성 상품*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_OG_IDX", referencedColumnName = "ogIdx")
    private OrderGs orderGs;



    /** 할인 전 상품 금액 spo_price*/
    private int ogPrice;

    /** 사용 예치금*/
    private int ogPoint;

    /** 사용 마일리지*/
    private int ogMileage;

    /** 할인 전 배송비*/
    private int ogDelivery;

    /** 사용 쿠폰 할인 상품금액*/
    private int ogCouponPrice;

    /** 사용 쿠폰 할인 배송비*/
    private int ogCouponDelivery;

    /** 적립 마일리지*/
    private int ogPlusMileage;

    private LocalDate ogDate;
    private LocalTime ogTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_G_IDX", referencedColumnName = "gIdx")
    private Goods goods;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_CP_IDX", referencedColumnName = "cpIdx")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_CD_IDX", referencedColumnName = "cdIdx")
    private CompanyDelivery companyDelivery;


}

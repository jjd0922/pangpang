package com.newper.entity;

import com.newper.constant.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    @JoinColumn(name = "OG_O_IDX", referencedColumnName = "OIdx")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_SPO_IDX", referencedColumnName = "spoIdx")
    private ShopProductOption shopProductOption;

    /** 결합 상품 그룹*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_OGG_IDX", referencedColumnName = "oggIdx")
    private OrderGsGroup orderGsGroup;

    /** 할인 전 상품 금액 spo_price*/
    private int ogPrice;
    /** 할인 전 배송비*/
    private int ogDelivery;
    /** 사용 예치금*/
    private int ogPoint;
    /** 사용 마일리지*/
    private int ogMileage;
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
    @JoinColumn(name = "OG_DN_IDX", referencedColumnName = "dnIdx")
    private DeliveryNum deliveryNum;


    /** 정산 관련 데이터 **/
    @Enumerated(EnumType.STRING)
    private CcgAdjust ogCalAdjust;
    private int ogCalAdjustCost;
    private int ogCalTotalCost;
    private int ogCalFinalCost;

    /** 정산상태 */
    @Enumerated(EnumType.STRING)
    private OgCalConfirmState ogCalConfirmState;
    private LocalDate ogCalConfirmDate;
    private String ogCalConfirmBy;
    private String ogCalConfirmMemo;

    /** 마감상태 **/
    @Enumerated(EnumType.STRING)
    private OgCalCloseState ogCalCloseState;
    private LocalDate ogCalCloseDate;
    private String ogCalCloseBy;
    private String ogCalCloseMemo;
}

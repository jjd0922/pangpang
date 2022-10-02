package com.newper.entity;

import com.newper.constant.*;
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

    /** 결합 상품 그룹 {spoIdx}_{spoIdx}|{cnt}*/
    private String ogSpo;
    private int ogPrice;
    private int ogPoint;
    private int ogMileage;
    private int ogCoupon;
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

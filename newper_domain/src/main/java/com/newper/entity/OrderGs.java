package com.newper.entity;

import com.newper.constant.PDelType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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

    private Integer ogPrice;
    private Integer ogPoint;
    private Integer ogMileage;
    private Integer ogCoupon;
    private LocalDate ogDate;
    private LocalTime ogTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_G_IDX", referencedColumnName = "gIdx")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_DN_IDX", referencedColumnName = "dnIdx")
    private DeliveryNum deliveryNum;

    /** shopProductOption, ogPrice 같이 세팅*/
    public void setShopProductOption(ShopProductOption shopProductOption) {
        setOgPrice(shopProductOption.getSpoPrice());

        this.shopProductOption = shopProductOption;
    }





}

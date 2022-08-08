package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShopProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spoIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPO_SPA_IDX", referencedColumnName = "spaIdx")
    private ShopProductAdd shopProductAdd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPO_GS_IDX", referencedColumnName = "gsIdx")
    private GoodsStock goodsStock;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String spoName;

    private Integer spoMoney;


}

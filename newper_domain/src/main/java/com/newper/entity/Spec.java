package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/** 스펙 그룹*/
@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Spec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specIdx;

    private String specConfirm;
    private String specLookup;

    /** 판매확정 스펙*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sellSpec", cascade = CascadeType.DETACH)
    private List<Goods> sellGoodsList;
    /** 재고 스펙*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stockSpec", cascade = CascadeType.DETACH)
    private List<Goods> stockGoodsList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "spec", cascade = CascadeType.DETACH)
    private List<GoodsStock> goodsStockList;
}

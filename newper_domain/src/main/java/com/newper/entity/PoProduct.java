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
public class PoProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ppIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PP_PO_IDX", referencedColumnName = "poIdx")
    private Po po;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PP_P_IDX", referencedColumnName = "pIdx")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PP_SPEC_IDX", referencedColumnName = "specIdx")
    private Spec spec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PP_SPEC_IDX2", referencedColumnName = "specIdx")
    private Spec spec2;

    private String ppOption;
    private int ppCost;
    private int ppCount;
    private int ppFixCost;
    private int ppPaintCost;
    private int ppProcessCost;
    private String ppMemo;
    private int ppSellPrice;
    private float ppProfitTarget;
}

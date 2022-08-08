package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class GoodsStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gsIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GS_P_IDX", referencedColumnName = "pIdx")
    private Product product ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GS_SPEC_IDX", referencedColumnName = "specIdx")
    private Spec spec ;

    private Integer gsRank;

    private String gsSale;

    private String gsOption;

    private Long gsStock;

    private Integer gsOriginalPrice;

    private Integer gsPrice;


}

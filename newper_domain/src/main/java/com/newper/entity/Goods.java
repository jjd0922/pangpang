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
public class Goods {
    @Id
    private BigInteger gidx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_IP_IDX", referencedColumnName = "ipIdx")
    private InProduct inProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_LOC_IDX", referencedColumnName = "locIdx")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_GS_IDX", referencedColumnName = "gsIdx")
    private  GoodsStock goodsStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_SPEC_IDX", referencedColumnName = "specIdx")
    private  Spec spec;

    private String gBarcode;
    private String ajBarcode;
    private String gState;
    private String gMemo;
    private String gRank;
    private String gVendor;
    private String gOption;





}

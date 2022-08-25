package com.newper.entity;

import com.newper.constant.GRank;
import com.newper.constant.GState;
import com.newper.constant.GStockState;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gidx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_P_IDX", referencedColumnName = "pIdx")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_PO_IDX", referencedColumnName = "poIdx")
    private Po po;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_POR_IDX", referencedColumnName = "porIdx")
    private PoReceived poReceived;

    /** 판매 확정 스펙*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_SELL_SPEC_IDX", referencedColumnName = "specIdx")
    private Spec sellSpec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_LOC_IDX", referencedColumnName = "locIdx")
    private Location location;

    /** 재고 스펙*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_STOCK_SPEC_IDX", referencedColumnName = "specIdx")
    private Spec stockSpec;

    /** 재고 코드*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_GS_IDX", referencedColumnName = "gsIdx")
    private GoodsStock goodsStock;

    private String gBarcode;
    private String gAjBarcode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private GState gState = GState.RECEIVED;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private GStockState gStockState = GStockState.N;



     private String gMemo;




    @Enumerated(EnumType.STRING)
    private GRank gRank;
    /*private String gVendor;*/

    @Builder.Default
    private List<String> gOption = new ArrayList<>();


    @Builder.Default
    private Map<String, Object> gDt = new HashMap<>();
    private String gImei;





}

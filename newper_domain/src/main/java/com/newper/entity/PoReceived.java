package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PoReceived {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer porIdx;
    private Integer porCount;
    private Integer porCost;
    private String porMemo;
    private Integer porSellPrice;
    private float porProfitTarget;
    private List<Map<String, Object>> porOption;
//    private LocalDate porDate;
//    private LocalTime porTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POR_P_IDX", referencedColumnName = "pIdx")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POR_PO_IDX", referencedColumnName = "poIdx")
    private Po po;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POR_PP_IDX", referencedColumnName = "ppIdx")
    private PoProduct poProduct;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "poReceived", cascade = CascadeType.DETACH)
    private List<Goods> goodsList;
}

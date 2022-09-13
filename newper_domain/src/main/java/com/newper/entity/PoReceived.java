package com.newper.entity;

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
public class PoReceived {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer porIdx;
    private Integer porCount;
    private String porMemo;
    private LocalDate porDate;
    private LocalTime porTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POR_P_IDX", referencedColumnName = "pIdx")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POR_PO_IDX", referencedColumnName = "poIdx")
    private Po po;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POR_IN_SPEC_IDX", referencedColumnName = "specIdx")
    private Spec spec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POR_PROCESS_SPEC_IDX2", referencedColumnName = "specIdx")
    private Spec spec2;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "poReceived", cascade = CascadeType.DETACH)
    private List<Goods> goodsList;
}

package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table (name = "po_estimate_product")
public class EstimateProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pepIdx;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PEP_PE_IDX", referencedColumnName = "peIdx")
    private Estimate estimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PEP_P_IDX", referencedColumnName = "pIdx")
    private Product product;

    private int pepCost;
    private int pepCount;
}



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
public class PoEstimateProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pepIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEP_PE_IDX", referencedColumnName = "peIdx")
    private PoEstimate poEstimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEP_P_IDX", referencedColumnName = "pIdx")
    private Product product;

    private Integer pepCount;

    private Integer pepCost;

}

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
public class InProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ipIdx;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IG_IDX", referencedColumnName = "igIdx")
    private InGroup inGroup;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_IDX", referencedColumnName = "pIdx")
    private  Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IP_SPEC_IDX", referencedColumnName = "specIdx")
    private  Spec spec;

    private Integer ipCount;

}

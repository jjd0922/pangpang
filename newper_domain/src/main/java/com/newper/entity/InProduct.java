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
    @JoinColumn(name = "IP_IG_IDX", referencedColumnName = "igIdx")
    private InGroup inGroup;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IP_P_IDX", referencedColumnName = "pIdx")
    private Product product;

    private Integer ipCount;

}

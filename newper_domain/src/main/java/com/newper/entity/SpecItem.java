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
public class SpecItem {
    @Id
    private Long speciIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPECI_SPEC_IDX", referencedColumnName = "specIdx")
    private Spec spec;

    @ManyToOne
    @JoinColumn(name = "SPECI_SPECL_IDX", referencedColumnName = "speclIdx")
    private SpecList specList;

    private int speciOrder;
}
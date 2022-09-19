package com.newper.entity;

import com.newper.constant.PsType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProcessSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer psIdx;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PS_PN_IDX", referencedColumnName = "pnIdx")
    private ProcessNeed processNeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PS_BOM_IDX", referencedColumnName = "speclIdx")
    private SpecList specList;

    private PsType psType;
    private int psExpectedCost;
    private int psRealCost;
    private String psRemove;
}

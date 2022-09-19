package com.newper.entity;

import com.newper.constant.PnType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProcessNeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pnIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PN_G_IDX", referencedColumnName = "gIdx")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PN_PG_IDX", referencedColumnName = "pgIdx")
    private ProcessGroup processGroup;

    private String pnType;

    private Integer pnCount;

    private String pnContent;

    private Integer pnExpectedCost;

    private Integer pnRealCost;

    private String pnLast;

    private String pnProcess;

    private Map<String, Object> pnJson;


}

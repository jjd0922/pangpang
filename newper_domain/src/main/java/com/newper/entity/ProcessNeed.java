package com.newper.entity;

import com.newper.constant.PnProcess;
import com.newper.constant.PnState;
import com.newper.constant.PnType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Map;

@Entity
@DynamicInsert
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PnType pnType = PnType.PROCESS;

    private Integer pnCount;

    private String pnContent;

    private Integer pnExpectedCost;

    private Integer pnRealCost;

    private Integer pnLast;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PnProcess pnProcess = PnProcess.BEFORE;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PnState pnState = PnState.NEED;

    private Map<String, Object> pnJson;


}

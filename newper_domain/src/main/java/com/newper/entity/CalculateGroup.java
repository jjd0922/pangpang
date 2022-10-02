package com.newper.entity;

import com.newper.constant.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CalculateGroup {
    /** 매입정산 */
    @Id
    @GeneratedValue
    private Integer ccgIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CCG_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @Enumerated(EnumType.STRING)
    private CcgConfirmState ccgConfirmState;

    @Enumerated(EnumType.STRING)
    private CcgCloseState ccgCloseState;

    @Enumerated(EnumType.STRING)
    private CcCalType ccgCalType;

    @Enumerated(EnumType.STRING)
    private CcCycle ccgCycle;

    @Enumerated(EnumType.STRING)
    private CcgType ccgType;

    @Enumerated(EnumType.STRING)
    private CcgAdjust ccgAdjust;

    private LocalDate ccgStartDate;
    private LocalDate ccgEndDate;
    private LocalDate ccgDueDate;


    private String ccgBank;
    private String ccgAccount;
    private String ccgName;

    private int ccgDeliveryCost;
    private int ccgDeliveryRefundCost;
    private int ccgInstallCost;
    private int ccgTotalCost;
    private int ccgAdjustCost;
    private int ccgFinalCost;

    private String ccgMemo;
    private String ccgCloseMemo;
    private String ccgPeriod;

    private LocalDate ccgConfirmDate;
    private LocalTime ccgConfirmTime;
    private String ccgConfirmBy;

    private LocalDate ccgCloseDate;
    private LocalTime ccgCloseTime;
    private String ccgCloseBy;

    private LocalDate ccgCreatedDate;
    private LocalTime ccgCreatedTime;

    private LocalDate ccgTaxDate;

}

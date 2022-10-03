package com.newper.entity;

import com.newper.constant.CcgAdjust;
import com.newper.constant.CcgCloseState;
import com.newper.constant.CcgConfirmState;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CalculateSales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ccsIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CCS_OG_IDX", referencedColumnName = "ogIdx")
    private OrderGs orderGs;


    @Enumerated(EnumType.STRING)
    private CcgAdjust ccsAdjust;
    private int ccsPrice;
    private int ccsDelivery;
    private int ccsFee;
    private int ccsAdjustCost;
    private int ccsFinalCost;

    /** 정산상태 */
    @Enumerated(EnumType.STRING)
    private CcgConfirmState ccsConfirmState;
    private LocalDate ccsConfirmDate;
    private String ccsConfirmBy;
    private String ccsConfirmMemo;

    /** 마감상태 **/
    @Enumerated(EnumType.STRING)
    private CcgCloseState ccsCloseState;
    private LocalDate ccsCloseDate;
    private String ccsCloseBy;
    private String ccsCloseMemo;
}

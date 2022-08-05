package com.newper.entity;

import com.newper.constant.*;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "company_contract")
@ToString
public class Contract extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ccIdx;

    @Enumerated(EnumType.STRING)
    private CcFeeType ccFeeType;

    @Enumerated(EnumType.STRING)
    private CcState ccState;

    @Enumerated(EnumType.STRING)
    private CcCycle ccCycle;

    @Enumerated(EnumType.STRING)
    private CcCalType ccCalType;

    @Enumerated(EnumType.STRING)
    private CcType ccType;

    private String ccTaxDate;
    private String ccBaseDate;
    private String ccStart;
    private String ccEnd;
    private float ccRates;
    private int ccCost;
    private String ccNum;
    private String ccFile;
    private String ccFileName;
    private String ccName;

    @Transient
    private String ccFileOri;
    @Transient
    private String ccFileNameOri;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CC_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CC_CE_IDX", referencedColumnName = "ceIdx")
    private CompanyEmployee companyEmployee;

    /** 상태, 내부담당자, 거래처 정산 담당자 정보만 수정 가능**/
    public void updateContract(Contract contract) {
        setCcState(contract.ccState);
    }
}

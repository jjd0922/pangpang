package com.newper.entity;

import com.newper.constant.*;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CC_CE_IDX", referencedColumnName = "ceIdx")
    private CompanyEmployee companyEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CC_U_IDX", referencedColumnName = "uIdx")
    private User user;

    @PrePersist
    @PreUpdate
    public void preSave() {
        if (getCompany() == null) {
            throw new MsgException("상호법인명을 입력해주세요.");
        } else if (!StringUtils.hasText(getCcName())) {
            throw new MsgException("계약명을 입력해주세요.");
        } else if (getCcType() == null) {
            throw new MsgException("계약구분을 선택해주세요.");
        } else if (getCcState() == null) {
            throw new MsgException("상태를 선택해주세요.");
        } else if (getCcCycle() == null) {
            throw new MsgException("정산주기를 선택해주세요.");
        } else if (getCcCalType() == null) {
            throw new MsgException("정산방식을 선택해주세요.");
        } else if (getCcStart() == null || getCcEnd() == null) {
            throw new MsgException("올바른 보증기간을 선택해주세요.");
        } else if (getCcFeeType() == null) {
            throw new MsgException("정액/정률을 선택해주세요.");
        } else if (getCcRates() < 0) {
            throw new MsgException("올바른 기준수수료율을 입력해주세요.");
        } else if (getCcCost() < 0) {
            throw new MsgException("올바른 기준수수료액을 입력해주세요.");
        } else if (!StringUtils.hasText(getCcNum())) {
            throw new MsgException("계약번호를 확인해주세요.");
        }
    }

    /** 상태, 내부담당자, 거래처 정산 담당자 정보만 수정 가능**/
    public void notUpdate(Contract contract) {
        setCompany(contract.getCompany());
        setCcName(contract.getCcName());
        setCcType(contract.getCcType());
        setCcStart(contract.getCcStart());
        setCcNum(contract.getCcNum());
    }
}

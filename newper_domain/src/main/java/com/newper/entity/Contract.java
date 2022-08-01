package com.newper.entity;

import com.newper.constant.*;
import com.newper.entity.common.Address;
import com.newper.entity.common.BaseEntity;
import com.newper.entity.common.CreatedEntity;
import com.newper.entity.common.ModifiedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

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
    private String ccContractStart;
    private String ccContractEnd;
    private float ccrates;
    private int ccCose;
    private String ccContractNum;
    private String ccContractFile;
    private String ccName;
    private String ccOutName;
    private String ccOutDepart;
    private String ccOutPostion;
    private String ccOutMail;
    private String ccOutPhone;
    private String ccOutTel;
    private String ccOutMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CC_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CC_CE_IDX", referencedColumnName = "ceIdx")
    private CompanyEmployee employee;
}

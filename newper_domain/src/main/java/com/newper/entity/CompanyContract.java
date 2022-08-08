package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CompanyContract extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ccIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CC_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CC_CE_IDX", referencedColumnName = "ceIdx")
    private CompanyEmployee companyEmployee;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CC_U_IDX", referencedColumnName = "uIdx")
    private User user;

    private String ccCtType;

    private String ccState;

    private String ccCycle;

    private String ccCalType;

    private String ccType;

    private String ccTaxDate;

    private String ccBaseDate;

    private Date ccContractStart;

    private Date ccContractEnd;

    private String ccFeeType;

    private Float ccRates;

    private Integer ccCost;

    private String ccContractNum;

    private String ccContractFile;

    private String ccName;




}

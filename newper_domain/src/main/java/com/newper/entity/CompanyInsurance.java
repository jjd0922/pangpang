package com.newper.entity;

import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CompanyInsurance extends CreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ciIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CI_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String ciType;

    private String ciInsuranceState;

    private String ciCompany;

    private String ciName;

    private String ciNum;

    private Date ciStartDate;

    private Date ciEndDate;

    private Long ciFee;

    private Float ciPercent;

    private Long ciMoney;

    private String ciFile;

    private String ciMemo;

    private String ciState;



}

package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CalculatepGroup {

    @Id
    @GeneratedValue
    private Integer cgIdx;

    private String cgCode;

    private String cgState;

    private Integer cgProductPrice;

    private Integer cgPrice;

    private String cgReason;

    private Integer cgLogPrice;

    private String cgLocReason;

    private Integer cgTaxPrice;

    private String cgTaxReason;

    private Integer cgDelPrice;

    private String cgDelReason;

    private Integer cgConfirmPrice;

    private String cgTaxFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CG_CE_IDX", referencedColumnName = "ceIdx")
    private CompanyEmployee companyEmployee;

    private String cgCalFile;

    private String cgCancelReason;

    private String cgMemo;

    private LocalDate cgDate;

    private LocalDate cgCompleteDate;

    private LocalDate cgCancelDate;

    private LocalDate cgLog;

}

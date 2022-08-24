package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CU_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    private String cuName;

    private String cuId;

    private String cuPw;

    private String cuTelecom;

    private String cuPhone;

    private String cuEmail;

    private String cuRate;

    private String cuState;

    private String cuBirth;

    private String cuGender;

    private String cuJoinDate;

    private String cuJoinTime;

    private String cuLastDate;

    private String cuLastTime;

    private Integer cuPoint;

    private Integer cuMileage;

    private String cuPwChange;

    private String cuMarketingAMail;

    private String cuMarketingMailCancel;

    private String cuMarketingSms;

    private String cuMarketingSmsCancel;

    private String cuCi;

    private String cuDi;





}

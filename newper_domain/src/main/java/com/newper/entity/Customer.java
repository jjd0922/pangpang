package com.newper.entity;

import com.newper.constant.CuGender;
import com.newper.constant.CuState;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private CuState cuState;

    private LocalDate cuBirth;

    @Enumerated(EnumType.STRING)
    private CuGender cuGender;

    private LocalDate cuJoinDate;

    private LocalTime cuJoinTime;

    private LocalDate cuLastDate;

    private LocalTime cuLastTime;

    private Integer cuPoint;

    private Integer cuMileage;

    private LocalDate cuPwChange;
    private boolean cuMarketingMail;
    private String cuMarketingMailCancel;
    private boolean cuMarketingSms;
    private String cuMarketingSmsCancel;
    private String cuCi;
    private String cuDi;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "couponGroup")
    private List<Coupon> couponList;

    /** 로그인 성공시 data update*/
    public void login(){
        LocalDateTime now = LocalDateTime.now();
        setCuLastDate(now.toLocalDate());
        setCuLastTime(now.toLocalTime());
    }

}

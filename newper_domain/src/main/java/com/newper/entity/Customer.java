package com.newper.entity;

import com.newper.constant.CuGender;
import com.newper.constant.CuRate;
import com.newper.constant.CuState;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


@Entity
@DynamicInsert
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
    @ColumnTransformer(write = "SHA2(?,512)")
    private String cuPw;
    private String cuTelecom;
    private String cuPhone;
    private String cuEmail;
    @Enumerated(EnumType.STRING)
    private CuRate cuRate;

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

    public void join(String phone, String email, Map<String,Object> map) {
        setCuPhone(phone);
        setCuEmail(email);
        setCuState(CuState.NORMAL);
        setCuRate(CuRate.SPECIAL); // 이게 제일 낮은지 확인
        setCuCi(getCuId());// 임시값.
        setCuMileage(0);
        setCuPoint(0);
        setCuDi(map.get("cuPw").toString()); // 임시값
        setCuJoinDate(LocalDate.now());
        setCuJoinTime(LocalTime.now());
        setCuLastDate(LocalDate.now());
        setCuLastTime(LocalTime.now());
        setCuPwChange(LocalDate.now());
        setShop(Shop.builder().shopIdx(1).build()); // 임시

        // notnull위해서 잠시
        setCuGender(CuGender.M);

    }

}

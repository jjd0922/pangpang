package com.newper.entity;

import com.newper.constant.CuGender;
import com.newper.constant.CuRate;
import com.newper.constant.CuState;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private LocalDate cuMarketingMailDate;
    private boolean cuMarketingSms;
    private LocalDate cuMarketingSmsDate;
    private String cuRecommender;
    private String cuCi;
    private String cuDi;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Coupon> couponList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Review> reviews;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Qna> qnaList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<QnaSp> qnaSpList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Cart> cartList;

    @PrePersist
    @PreUpdate
    public void preSave(){
        setCuPhone(getCuPhone().replaceAll("[^0-9]", ""));
    }


    /** 로그인 성공시 data update*/
    public void login(){
        LocalDateTime now = LocalDateTime.now();
        setCuLastDate(now.toLocalDate());
        setCuLastTime(now.toLocalTime());
    }

    public void join(Map<String,Object> nice) {
        setCuName(nice.get("NAME").toString());
        setCuPhone(nice.get("MOBILE_NO").toString());
        setCuTelecom(nice.get("MOBILE_CO").toString());
        setCuCi(getCuId()); // 임시값
        setCuDi(nice.get("DI").toString());
        setCuBirth(LocalDate.parse(nice.get("BIRTHDATE").toString(), DateTimeFormatter.ofPattern("yyyyMMdd")));
        setCuState(CuState.NORMAL);
        setCuRate(CuRate.SPECIAL); // 이게 제일 낮은지 확인
        setCuMileage(0);
        setCuPoint(0);
        setCuJoinDate(LocalDate.now());
        setCuJoinTime(LocalTime.now());
        setCuLastDate(LocalDate.now());
        setCuLastTime(LocalTime.now());
        setCuPwChange(LocalDate.now());
        setCuMarketingMailDate(LocalDate.now());
        setCuMarketingSmsDate(LocalDate.now());

        if (nice.get("GENDER").toString().equals("0")) {
            setCuGender(CuGender.F);
        } else {
            setCuGender(CuGender.M);
        }
    }

    /** 휴대폰 번호 - 붙인 포맷으로 가져오기*/
    public String getPhoneFormat(){
        return getCuPhone().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
    }

    /** 휴대폰 번호 3등분 끊어서 가져오기*/
    public String[] getPhoneArr() {
        return getPhoneFormat().split("-");
    }
}

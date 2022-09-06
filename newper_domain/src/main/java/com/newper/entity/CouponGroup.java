package com.newper.entity;

import com.newper.constant.CpgDiscountType;
import com.newper.constant.CpgDuplicate;
import com.newper.constant.CpgState;
import com.newper.constant.CpgType;
import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CouponGroup extends CreatedEntity {

    @Id @GeneratedValue
    private Long cpgIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CPG_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CPG_HW_IDX", referencedColumnName = "hwIdx")
    private Hiworks hiworks;

    private String cpgName;

    @Enumerated
    private CpgState cpgState;
    @Enumerated
    private CpgDuplicate cpgDuplicate;
    @Enumerated
    private CpgDiscountType cpgDiscountType;
    @Enumerated
    private CpgType cpgType;

    private LocalDate cpgStartDate;
    private LocalDate cpgEndDate;
    private Integer cpgMin;
    private Integer cpgMax;
    private Float cpgMoney;
    private Integer cpgCnt;

    @Builder.Default
    private Integer cpgUsedCnt = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "couponGroup")
    private List<Coupon> couponList;

}
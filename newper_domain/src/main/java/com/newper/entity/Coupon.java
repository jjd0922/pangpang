package com.newper.entity;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CP_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CP_PAY_IDX", referencedColumnName = "payIdx")
    private Payment payment;

    private String cpName;

    private Integer cpMin;

    private Integer cpMax;

    private Float cpMoney;

    private Date cpEndDate;


}

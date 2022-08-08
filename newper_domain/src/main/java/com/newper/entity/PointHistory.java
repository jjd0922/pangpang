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
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_PAY_IDX", referencedColumnName = "payIdx")
    private Payment payment;

    private String pointType;

    private String pointUse;

    private Integer pointAmount;

    private String pointMemo;

}

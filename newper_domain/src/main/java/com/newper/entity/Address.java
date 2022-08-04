package com.newper.entity;

import com.newper.entity.common.BaseEntity;
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
public class Address extends BaseEntity {

    @Id
    private BigInteger adIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AD_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    private String adBasic;
    private String adTitle;

    @Embedded
    private com.newper.entity.common.Address address;

    private String adPhone;
    private String adMemo;
    private String adEntrance;
    private String adName;




}

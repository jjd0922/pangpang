package com.newper.entity;

import com.newper.entity.common.AddressEmb;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AD_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    private String adBasic;
    private String adTitle;

    @Embedded
    private AddressEmb address;

    private String adPhone;
    private String adMemo;
    private String adEntrance;
    private String adName;




}

package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PayInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int piIdx;

    private String pgName;

    private String pgId;

    private String pgKey;

    private String pgSecret;

    private String pgMemo;

    private String pgState;

    private int pgDate;

    private String pgDateUnit;

    private Float pgFeeCard;

    private byte pgFeeCardUnit;

    private String pgFeeCardVat;

    private Float pgFeeBank;

    private byte pgFeeBankUnit;

    private String pgFeeBankPre;

    private String pgFeeBankVat;

    private Float pgFeeVa;

    private byte pgFeeVaUnit;

    private String pgFeeVaPre;

    private String pgFeeVaVat;











}

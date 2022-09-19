package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Pg extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pgIdx;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pg", cascade = CascadeType.ALL)
    private List<Shop> shopList;

}

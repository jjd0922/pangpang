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
public class Agreement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agmIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AGM_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    private String agmType;

    private String agmVer;

    private String agmContent;

}


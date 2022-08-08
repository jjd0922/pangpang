package com.newper.entity;

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
public class ShopProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SP_CATE_IDX", referencedColumnName = "cateIdx")
    private Category category;

    private String spName;

    private String spState;

    private Integer spMinPrice;

    private Integer spMaxPrice;

    private String spBaseName;

    private Integer spBasePrice;

    private Float spPercent;

    private Integer spQuotaOnce;

    private String spTag;
}

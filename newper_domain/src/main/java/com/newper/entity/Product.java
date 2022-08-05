package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table (name = "product")
public class Product extends BaseEntity {
    @Id
    private Integer pIdx;

    private String pCode;
    private Integer pNaverPrice;
    private long pSellPrice;
    private String pType1;
    private String pType2;
    private String pName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String pInfo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String pOption;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_CATE_IDX", referencedColumnName = "cateIdx")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_CATE_IDX2", referencedColumnName = "cateIdx")
    private Category category2;
}
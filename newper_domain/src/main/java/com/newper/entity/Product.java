package com.newper.entity;

import com.newper.constant.PState;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pIdx;

    /** 상품분류 소분류 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_CATE_IDX", referencedColumnName = "cateIdx")
    private Category category;

    /** 브랜드 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_CATE_IDX2", referencedColumnName = "cateIdx")
    private Category brand;

    private String pCode;
    private String pName;

    @Enumerated(EnumType.STRING)
    private PState pState;

    private String pModel;
    private boolean pUseStock;

    private String pType1;
    private String pType2;
    private String pType3;

    private String pInfo;

    private String pOption;




}
package com.newper.entity;

import com.newper.constant.PState;
import com.newper.constant.PType1;
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

    @Enumerated(EnumType.STRING)
    private PType1 pType1;

    @Enumerated(EnumType.STRING)
    private String pType2;

    @Enumerated(EnumType.STRING)
    private String pType3;

    /** 입점사 idx */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_COM_IDX", referencedColumnName = "comIdx")
    private Company storeName;

    /** 제조사 idx */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_COM_IDX2", referencedColumnName = "comIdx")
    private Company manufactureName;

    /** A/S업체 idx */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_COM_IDX3", referencedColumnName = "comIdx")
    private Company afterServiceName;

    private String pThumbFile1;
    private String pThumbFileName1;

    private String pThumbFile2;
    private String pThumbFileName2;

    private String pThumbFile3;
    private String pThumbFileName3;

    private String pThumbFile4;
    private String pThumbFileName4;

    private String pThumbFile5;
    private String pThumbFileName5;

    private String pThumbFile6;
    private String pThumbFileName6;

    private String pContent1;
    private String pContent2;
    private String pContent3;

    private String pMemo;
    private String pTag;
    private String pBlogUrl;
    private String pPriceUrl;
    private String pYoutubeUrl;

    private Integer pCost;
    private Integer pRetailPrice;
    private Integer pSellPrice;
    private Integer pDelPrice;

    private String pDelCompany;

    private int pDelTogether;

    private boolean pFreeInterest;
    private boolean pDelFree;

    private String pNaver;

    private String pInfo;
    private String pOption;




}
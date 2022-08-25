package com.newper.entity;

import com.newper.constant.PState;
import com.newper.constant.PType1;
import com.newper.constant.PType2;
import com.newper.constant.PType3;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private PType2 pType2;

    @Enumerated(EnumType.STRING)
    private PType3 pType3;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.DETACH)
    private List<Goods> goodsList;


/*    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Product", cascade = CascadeType.DETACH)
    private List<InProduct> inProductList;*/


    @PrePersist
    @PreUpdate
    public void preSave(){
        if (!StringUtils.hasText(getPName())) {
            throw new MsgException("상품명을 입력해주세요.");
        }
        if (getPType1() == null) {
            throw new MsgException("정산구분(품목구분1)을 선택해주세요.");
        }
        if (getPType2() == null) {
            throw new MsgException("상품분류(품목구분2)를 선택해주세요.");
        }
        if (getPType3() == null) {
            throw new MsgException("물류타입(품목자산구분)을 선택해주세요.");
        }

    }


    /** 고시정보. json map*/
    @Builder.Default
    private Map<String,Object> pInfo = new HashMap<String, Object>();
    private Map<String,Object> pOption = new HashMap<>();
    private Map<String,Object> pNaver = new HashMap<>();




}
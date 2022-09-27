package com.newper.entity;

import com.newper.constant.*;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @Enumerated(EnumType.STRING)
    private PDelType pDelType;

    /** 입점사 idx */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_COM_IDX", referencedColumnName = "comIdx")
    private Company storeName;

/*    *//** 제조사 idx *//*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_COM_IDX2", referencedColumnName = "comIdx")*/
    private String pComManufacturer;

    /** A/S업체 idx */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_COM_IDX2", referencedColumnName = "comIdx")
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

    private int pCost;
    private int pRetailPrice;
    private int pSellPrice;
    private int pDelPrice;

    @Enumerated(EnumType.STRING)
    private PDelCompany pDelCompany;

    private int pDelTogether;

    private boolean pFreeInterest;
    private int pDelPriceCancel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.DETACH)
    private List<Goods> goodsList;


    /** 고시정보. json map*/
    @Builder.Default
    private Map<String,Object> pInfo = new HashMap<String, Object>();
    /** key(색상명) : value(RGB) */
    @Builder.Default
    private Map<String, Object> pColor = new LinkedHashMap<>();
    /**<pre>
     * {title : title, values :["opt1", "opt2"]}
     * </pre> */
    private List<Map<String,Object>> pOption = new ArrayList<>();
    private Map<String,Object> pNaver = new HashMap<>();


    @PrePersist
    @PreUpdate
    public void preSave(){
        if (!StringUtils.hasText(getPName())) {
            throw new MsgException("상품명을 입력해주세요.");
        }
        if (getCategory() == null) {
            throw new MsgException("카테고리 소분류를 선택해주세요.");
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
        if (getPDelType() == null) {
            throw new MsgException("배송타입을 선택해주세요.");
        }
    }

    /** 해당 옵션 문자로 return*/
    public String getOptionValues(String optionName){
        Map<String, Object> optionMap = pOption.stream().filter(map -> {
            return ((String) map.get("title")).equals(optionName);
        }).findAny().get();
        List<String> list = (List)optionMap.get("values");
        if(list.size()==0){
            return "";
        }else{
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<list.size(); i++){
                sb.append(list.get(i)+",");
            }
            return sb.toString().substring(0, sb.length() - 1);
        }
    }




}
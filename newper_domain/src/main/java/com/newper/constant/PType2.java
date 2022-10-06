package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 상품분류(품목구분2) PRODUCT.P_TYPE2 */
@Getter
@AllArgsConstructor
public enum PType2 implements EnumOption {

    /** 상품*/
    PRODUCT("상품")
    /** 부품*/
    ,PARTS("부품")
    /** 소모품*/
    ,EXPENDABLES("소모품")
    /** 사은품*/
    ,GIFTS("사은품")
    ,SERVICE("서비스")
    /** 해당사항없음*/
    ,ELSE("해당사항없음")
    ;
    private String option;

}

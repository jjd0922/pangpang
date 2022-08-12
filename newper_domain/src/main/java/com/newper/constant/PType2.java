package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 상품분류(품목구분2) PRODUCT.P_TYPE2 */
@Getter
@AllArgsConstructor
public enum PType2 {

    PRODUCT("상품")
    ,PARTS("부품")
    ,EXPENDABLES("소모품")
    ,GIFTS("사은품")
    ,ELSE("해당사항없음")
    ;
    private String option;

}

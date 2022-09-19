package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 물류타입(품목자산구분) PRODUCT.P_TYPE3 */
@Getter
@AllArgsConstructor
public enum PType3 implements EnumOption {

    /** 자사물류 */
    OWNLOGI("자사물류")
    /** 3PL물류 */
    ,THREEPLLOGI("3PL물류")
    /** 외부물류 */
    ,EXTERNALLOGI("외부물류")
    /** 해당사항없음 */
    ,ORELSE("해당사항없음")
    ;
    private String option;

}

package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 물류타입(품목자산구분) PRODUCT.P_TYPE3 */
@Getter
@AllArgsConstructor
public enum PType3 {

    OWNLOGI("자사물류")
    ,THREEPLLOGI("3PL물류")
    ,EXTERNALLOGI("외부물류")
    ,INSTALLLOGI("설치물류")
    ,ORELSE("해당사항없음")
    ;
    private String option;

}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 사용자 구분*/
@Getter
@AllArgsConstructor
public enum UType implements EnumOption {

    /** 내부 */
    INSIDE("내부")
    /** 외부 */
    ,OUTSIDE("외부")
    ;

    private String option;



}

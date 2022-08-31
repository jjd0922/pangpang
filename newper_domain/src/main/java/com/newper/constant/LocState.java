package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 창고 상태 location.LOC_STATE*/
@Getter
@AllArgsConstructor
public enum LocState implements EnumOption {

    /**정상*/
    NORMAL("정상")
    /**중지*/
    ,STOP("중지")
    ;

    private String option;



}

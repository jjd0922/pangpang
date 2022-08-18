package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 창고 상태 location.LOC_STATE*/
@Getter
@AllArgsConstructor
public enum LocState implements EnumOption {

    NORMAL("정상")
    ,STOP("중지");

    private String option;



}

package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 입고 그룹 상태*/
@Getter
@AllArgsConstructor
public enum IgState {

    /**입고전*/
    NONE("입고전")
    /**진행중*/
    ,ING("진행중")
    /**입고완료*/
    ,DONE("입고완료")
    ;

    private String option;
}

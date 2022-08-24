package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 입고 그룹 상태*/
@Getter
@AllArgsConstructor
public enum IgState {

     NONE("입고전")
    ,ING("진행중")
    ,DONE("입고완료")
    ;

    private String option;
}

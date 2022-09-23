package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 창고 이동 상태 location.LOC_MOVE_STATE*/
@Getter
@AllArgsConstructor
public enum LmState implements EnumOption {


    WORKING("작업중(출고)")

    ,FINISHWORK("작업완료(입고)")
    ,CANCELWORK("작업요청취소")
    ;

    private String option;



}

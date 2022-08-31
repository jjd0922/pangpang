package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 자산 상태*/
@Getter
@AllArgsConstructor
public enum GState implements EnumOption {

    /** 입고검수대기*/
    RECEIVED("입고검수대기")
    /** 입고검수요청*/
    ,CHECK_NEED("입고검수요청")
    /** 입고검수*/
    ,CHECK("입고검수")
    /** 공정*/
    ,PROCESS("공정")
    /** 재고인계요청*/
    ,STOCK_REQ("재고인계요청")
    /** 적재*/
    ,STOCK("적재")
    /** 반품필요*/
    ,CANCEL_NEED("반품필요")
    /** 반품*/
    ,CANCEL("반품")
    /** 망실*/
    ,LOSS("망실")
    ;

    private String option;

}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 자산 상태*/
@Getter
@AllArgsConstructor
public enum GState implements EnumOption {

    RECEIVED("입고")
    ,CHECK_NEED("입고검수요청")
    ,CHECK("입고검수")
    ,PROCESS("공정")
    ,CHECK_RE("재검수요청")
    ,CHECK_RE_PRO("재검수중")
    ,STOCK_REQ("재고인계요청")
    ,STOCK("적재")
    ,CANCEL_NEED("반품필요")
    ,CANCEL_REQ("반품요청")
    ,CANCEL("반품진행중")
    ,CANCEL_COMP("반품완료")
    ,LOSS("망실")
    ;

    private String option;

}
package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 자산 상태*/
@Getter
@AllArgsConstructor
public enum GState {

     RECEIVED("입고검수대기")
    ,CHECK_NEED("입고검수요청")
    ,CHECK("입고검수")

    ,PROCESS("공정")

    ,STOCK_REQ("재고인계요청")
    ,STOCK("적재")

    ,CANCEL_NEED("반품필요")
    ,CANCEL("반품")


    ,LOSS("망실")
    ;

    private String option;

}

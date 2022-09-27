package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 자산 상태*/
@Getter
@AllArgsConstructor
public enum GState implements EnumOption {

    RECEIVED("입고")

    ,CHECK_REQ("검수필요")
    ,CHECK_ING("검수중")
    ,CHECK_DONE("검수완료")

    ,PROCESS("공정")

    ,STOCK("상품화완료")

    ,CANCEL_NEED("반품필요")
    ,CANCEL_REQ("반품요청")
    ,CANCEL_ING("반품진행중")
    ,CANCEL_DONE("반품완료")

    ,LOST("망실");

    private String option;

}
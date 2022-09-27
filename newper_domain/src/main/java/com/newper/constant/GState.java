package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 자산 상태*/
@Getter
@AllArgsConstructor
public enum GState implements EnumOption {

    RECEIVED("입고")

    ,CHECK_REQ("입고검수요청")
    ,CHECK_ING("입고검수중")
    ,CHECK_DONE("입고검수완료")

    ,RE_CHECK_NEED("재검수필요")
    ,RE_CHECK_REQ("재검수요청")
    ,RE_CHECK_ING("재검수중")
    ,RE_CHECK_DONE("재검수완료")

    ,PROCESS("공정")

    ,STOCK("상품화완료")

    ,OUT_CHECK_REQ("출고전검수요청")
    ,OUT_CHECK_ING("출고전검수중")
    ,OUT_CHECK_DONE("출고전검수완료")

    ,CANCEL_NEED("입고반품필요")
    ,CANCEL_REQ("입고반품요청")
    ,CANCEL_ING("입고반품진행중")
    ,CANCEL_DONE("입고반품완료")

    ,LOST("망실");

    private String option;

}
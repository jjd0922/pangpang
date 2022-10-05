package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 자산 상태*/
@Getter
@AllArgsConstructor
public enum GState implements EnumOption {

     CHECK_NEED("입고검수필요")
    ,CHECK_REQ("입고검수요청")
    ,CHECK_ING("입고검수중")

    ,RE_CHECK_NEED("재검수필요")
    ,RE_CHECK_REQ("재검수요청")
    ,RE_CHECK_ING("재검수중")

    /** 입고검수 후 전부 PROCESS 상태로. 재검수는 공정있는 경우만 PROCESS상태로, 없는 경우 STOCK상태로 */
    ,PROCESS("공정")

    ,STOCK("상품화완료")

    ,CANCEL_NEED("입고반품필요")
    ,CANCEL_REQ("입고반품요청")
    ,CANCEL_ING("입고반품진행중")
    ,CANCEL_DONE("입고반품완료")

    ,LOST("망실");

    private String option;
}
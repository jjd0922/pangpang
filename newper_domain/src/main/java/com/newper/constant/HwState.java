package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**HIWORKS 결재 상태*/
@Getter
@AllArgsConstructor
public enum HwState {

    /**요청전*/
    BEFORE("요청전")
    /**요청*/
    ,REQ("요청")
    /**반려*/
    ,REJECT("반려")
    /**승인*/
    ,APPROVED("승인")

    ;

    private String option;
}

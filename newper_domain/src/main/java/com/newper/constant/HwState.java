package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**HIWORKS 결재 상태*/
@Getter
@AllArgsConstructor
public enum HwState {

     BEFORE("요청전")
    ,REQ("요청")
    ,REJECT("반려")
    ,APPROVED("승인")

    ;

    private String option;
}

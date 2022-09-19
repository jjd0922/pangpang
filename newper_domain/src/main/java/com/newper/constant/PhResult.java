package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhResult implements EnumOption {

     WAIT("결과대기")
    ,FAIL("실패")
    ,DONE("성공")
    ;

    private String option;
}

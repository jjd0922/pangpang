package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/** PAYMENT.PAY_STATE, PAYMENT.PAY_CANCEL_STATE*/
public enum PayState implements EnumOption {

     BEFORE("요청전")
    ,REQ("요청")
    ,SUCCESS("완료")
    ,FAIL("실패")
    ;

    private String option;
}

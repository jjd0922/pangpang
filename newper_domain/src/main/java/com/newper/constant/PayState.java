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

    /** 결제 금액이 일치하는 않는 경우 확인 필요*/
    ,ERROR("확인중")
    ;

    private String option;
}

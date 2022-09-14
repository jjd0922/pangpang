package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/** PAYMENT.PAY_STATE, PAYMENT.PAY_CANCEL_STATE*/
public enum PayMethod implements EnumOption {

     CARD("신용카드")
    ,BANK("계좌이체")
    ,ACCOUNT("가상계좌")
    ,PAY("페이")

    ;

    private String option;
}

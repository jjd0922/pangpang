package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhType {

     PAY("결제")
    ,CANCEL("취소")
    ;

    private String option;
}

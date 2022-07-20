package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** 거래처 상태. COMPANY.COM_STATE*/
@Getter
@AllArgsConstructor
public enum ComState {

    TEST("test")
    ,TEST2("test")
    ;


    private String value;

}

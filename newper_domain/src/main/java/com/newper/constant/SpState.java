package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpState implements EnumOption {

    N("판매중지")
    ,Y("판매")

    ;

    private String option;
}

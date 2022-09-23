package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpState implements EnumOption {

     Y("판매")
    ,N("판매중지")

    ;

    private String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OcType implements EnumOption {

    REFUND("환불"),
    CHANGE("교환")
    ;

    private String option;
}

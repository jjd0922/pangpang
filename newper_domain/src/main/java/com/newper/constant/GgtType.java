package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GgtType implements EnumOption {

    IN_CHECK("입고검수"),
    PROCESS("가공"),
    FIX("수리"),
    PAINT("도색"),
    RESELL("반품")
    ;

    private String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/** PROCESS_SPEC.PS_TYPE */
public enum PsType implements EnumOption {

    EXPECTED("가공예정"),
    REAL("가공확정");

    private String option;
}

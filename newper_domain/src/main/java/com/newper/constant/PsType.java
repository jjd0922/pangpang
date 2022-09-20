package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 상품 상태*/
@Getter
@AllArgsConstructor
/** PROCESS_SPEC.PS_TYPE */
public enum PsType implements EnumOption {

    EXPECTED("가공예정"),
    CONFIRM("가공확정")
    ;

    private String option;
}

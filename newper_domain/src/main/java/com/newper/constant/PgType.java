package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/** 공정필요 PROCESS_GROPU.PG_TYPE */
public enum PgType implements EnumOption {

    PAINT("도색"),
    FIX("수리"),
    PROCESS("가공");


    private String option;
}

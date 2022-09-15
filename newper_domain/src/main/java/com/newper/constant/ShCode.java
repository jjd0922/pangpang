package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShCode implements EnumOption {
    /**성공*/
    SUCCESS("SUCCESS"),
    /**실패*/
    FAIL("FAIL");

    public String option;
}

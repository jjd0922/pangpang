package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShCode {
    /**성공*/
    SUCCESS("SUCCESS"),
    /**실패*/
    FAIL("FAIL");

    public String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** SCHEDULE.S_STATE*/
@Getter
@AllArgsConstructor
public enum SState implements EnumOption{

    TEST("test")

    ;

    private String option;
}

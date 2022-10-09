package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OCancelState implements EnumOption {

    NONE(""),
    REQUEST("요청"),
    COMPLETE("완료")
    ;

    private String option;
}

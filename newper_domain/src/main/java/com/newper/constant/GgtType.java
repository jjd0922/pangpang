package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GgtType implements EnumOption {

    IN_CHECK("입고검수")
    ;

    private String option;
}

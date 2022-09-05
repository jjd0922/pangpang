package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ODeliveryState implements EnumOption {

    BEFORE("")
    ;

    private String option;
}

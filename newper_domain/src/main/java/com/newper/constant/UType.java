package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UType implements EnumOption {

    INSIDE("내부")
    ,OUTSIDE("외부");

    private String option;



}

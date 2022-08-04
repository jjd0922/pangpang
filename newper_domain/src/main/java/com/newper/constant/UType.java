package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UType implements EnumOption {

    Inside("내부")
    ,Outside("외부");

    private String option;



}

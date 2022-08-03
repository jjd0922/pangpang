package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CfType implements EnumOption {
    
    C("정액")
    ,R("정률");

    private String option;
}

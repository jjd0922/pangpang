package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CfType {
    
    C("정액")
    ,R("정률");

    private String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 거래처 수수료 구분*/
@Getter
@AllArgsConstructor
public enum CfType implements EnumOption {
    
    /**정액*/
    C("정액")
    /**정률*/
    ,R("정률")
    ;

    private String option;
}

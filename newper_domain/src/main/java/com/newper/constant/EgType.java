package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EgType implements EnumOption {
    COMMON("일반")
    ,PRODUCT("상품")
    ,APPLY("응모")
    ;


    private String option;
}

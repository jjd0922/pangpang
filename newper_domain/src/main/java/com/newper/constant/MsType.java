package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsType implements EnumOption {
     BANNER("배너")
    ,PRODUCT("상품")
    ,BOTH("기획전")
    ;

    private String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** AFTER_SERVICE.AS_DN_TYPE*/
@Getter
@AllArgsConstructor
public enum AsDnType implements EnumOption {

    NONE("미회수"),
    CUSTOMER("고객발송")
    ,COMPANY("직접회수");

    private String option;
}
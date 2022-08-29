package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 발주 구분. PO.PO_TYPE*/
@Getter
@AllArgsConstructor
public enum PoType implements EnumOption {

    /**일반 발주*/
    NORMAL("일반 발주")
    /**긴급 발주*/
    ,EMERGENCY("긴급 발주")

    ;

    private String option;
}

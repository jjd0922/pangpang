package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 송장 타입 DELIVERY_NUM.DN_TYPE*/
@Getter
@AllArgsConstructor
public enum OgdnType implements EnumOption {

    DELIVERY("배송"),
    REFUND("반품"),
    AS_IN("AS입고"),
    AS_OUT("AS출고");

    private String option;
}
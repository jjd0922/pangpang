package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 설치 / 배송 타입 DELIVERY_NUM.DN_TYPE*/
@Getter
@AllArgsConstructor
public enum DnType implements EnumOption {

    DELIVERY("배송"),
    INSTALL("설치");

    private String option;
}
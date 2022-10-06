package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 보내는 주체 타입 DELIVERY_NUM.DN_SENDER*/
@Getter
@AllArgsConstructor
public enum DnSender implements EnumOption {

    CUSTOMER("고객"),
    COMPANY("회사");

    private String option;
}
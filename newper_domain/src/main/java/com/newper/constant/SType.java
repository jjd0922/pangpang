package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**미팅구분*/
@Getter
@AllArgsConstructor
public enum SType implements EnumOption {

    /**초기*/
    BEGIN("초기"),
    /**상담*/
    CONSULT("상담"),
    /**협의*/
    DISCUSS("협의"),
    /**계약*/
    CONTRACT("계약"),
    /**중단*/
    STOP("중단"),
    /**완료*/
    COMPLETE("완료");

    private String option;
}

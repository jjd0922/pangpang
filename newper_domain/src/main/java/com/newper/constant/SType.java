package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**미팅구분*/
@Getter
@AllArgsConstructor
public enum SType implements EnumOption {

    BEGIN("초기"),
    CONSULT("상담"),
    DISCUSS("협의"),
    CONTRACT("계약"),
    STOP("중단"),
    COMPLETE("완료");

    private String option;
}

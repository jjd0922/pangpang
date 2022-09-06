package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**쿠폰 할인타입*/
@Getter
@AllArgsConstructor
public enum CpgDiscountType implements EnumOption {

    /**정액*/
    MONEY("정액"),
    /**정률*/
    RATE("정률");

    private String option;
}

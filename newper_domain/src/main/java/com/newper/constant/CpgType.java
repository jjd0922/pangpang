package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**쿠폰타입*/
@Getter
@AllArgsConstructor
public enum CpgType implements EnumOption {

    /**배송비형*/
    DELIVERY("배송비형"),
    /**할인형*/
    DISCOUNT("할인형"),
    /**카테고리형*/
    CATEGORY("카테고리형");

    private String option;
}

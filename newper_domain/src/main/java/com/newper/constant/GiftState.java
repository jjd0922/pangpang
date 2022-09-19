package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**상품권 상태*/
@Getter
@AllArgsConstructor
public enum GiftState implements EnumOption {

    /**대기*/
    WAITING("대기"),
    /**사용*/
    USED("사용"),
    /**폐기*/
    DISPOSAL("폐기"),
    /**만료*/
    EXPIRED("만료");

    private String option;
}

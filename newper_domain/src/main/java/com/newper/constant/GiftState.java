package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**상품권 상태*/
@Getter
@AllArgsConstructor
public enum GiftState implements EnumOption {

    WAITING("대기"),
    USED("사용"),
    DISPOSAL("폐기"),
    EXPIRED("만료");

    private String option;
}

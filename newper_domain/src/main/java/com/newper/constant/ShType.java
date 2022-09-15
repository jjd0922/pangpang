package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShType implements EnumOption {

    /**SMS*/
    SMS("SMS"),
    /**KAKAO*/
    KAKAO("KAKAO"),
    /**PUSH*/
    PUSH("PUSH");

    public String option;
}

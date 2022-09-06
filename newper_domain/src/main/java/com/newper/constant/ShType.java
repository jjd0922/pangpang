package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShType {

    /**SMS*/
    SMS("SMS"),
    /**KAKAO*/
    KAKAO("KAAKAO"),
    /**PUSH*/
    PUSH("PUSH");

    public String option;
}

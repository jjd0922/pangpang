package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum VsType implements EnumOption {

    SMS("SMS타입")
    ,KAKAO("KAKAO타입");

    private String option;

}

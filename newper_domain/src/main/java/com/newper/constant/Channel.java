package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 채널*/
@Getter
@AllArgsConstructor
public enum Channel implements EnumOption {
    OWN("자사몰")
    ,OUT("외부몰")
    ,VENDOR("벤더몰")
    ,NO("해당사항없음");

    private String option;
}

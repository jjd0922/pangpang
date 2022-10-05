package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** AFTER_SERVICE.AS_STATE*/
@Getter
@AllArgsConstructor
public enum AsState implements EnumOption {

    REQUEST("AS요청")
    ,IMPOSSIBLE("AS불가")
    ,COMPLETE("AS완료");

    private String option;
}
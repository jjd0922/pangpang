package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** CALCULATE.CGS_ADJUST 일반, 조정 여부 */
@Getter
@AllArgsConstructor
public enum CcgAdjust implements EnumOption {
     NORMAL("일반")
    ,ADJUST("조정")
    ;

    private String option;
}

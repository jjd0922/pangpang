package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OLocation implements EnumOption {

    /**자사몰*/
    OWN("자사몰")
    /**벤더사*/
    ,VENDOR("벤더몰")
    /**오프라인*/
    ,offline("오프라인")

    ;

    private String option;


}

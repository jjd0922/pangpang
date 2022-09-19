package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 채널. PO.PO_SELL_CHANNEL*/
@Getter
@AllArgsConstructor
public enum Channel implements EnumOption {
    /**자사몰*/
    OWN("자사몰")
    /**외부몰*/
    ,OUT("외부몰")
    /**벤더몰*/
    ,VENDOR("벤더몰")
    /**해당사항없음*/
    ,NO("해당사항없음")
    ;

    private String option;
}

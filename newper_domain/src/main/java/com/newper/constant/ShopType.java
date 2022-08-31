package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**분양몰 타입*/
@Getter
@AllArgsConstructor
public enum ShopType implements EnumOption {

    NORMAL("일반몰")
    ;

    private String option;
}

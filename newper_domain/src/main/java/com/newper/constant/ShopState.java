package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**분양몰 운영상태*/
@Getter
@AllArgsConstructor
public enum ShopState implements EnumOption {

     CLOSED("미운영")
    ,OPEN("운영")
    ;

    private String option;

}

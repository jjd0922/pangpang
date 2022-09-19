package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OLocation implements EnumOption {

     SHOP("쇼핑몰")
    ,OFFLINE("오프라인")
    ,SABANG("사방넷")
    ;

    private String option;


}

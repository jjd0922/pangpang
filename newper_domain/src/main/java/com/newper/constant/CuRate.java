package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**회원등급 customer.CU_RATE*/
@Getter
@AllArgsConstructor
public enum CuRate implements EnumOption {

    VVIP("VVIP")
    ,VIP("VIP")
    ,PLATINUM("플래티넘")
    ,SPECIAL("스페셜");

    private String option;


}

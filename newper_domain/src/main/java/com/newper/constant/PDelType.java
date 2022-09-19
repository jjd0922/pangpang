package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**상품. 배송타입*/
@Getter
@AllArgsConstructor
public enum PDelType implements EnumOption {
    /**배송상품*/
    DELIVERY("배송상품")
    /**설치상품*/
    ,INSTALL("설치상품")
    ;

    private String option;
}

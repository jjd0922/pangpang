package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CateType implements EnumOption {

    /**카테고리*/
    CATEGORY("카테고리")
    /**브랜드*/
    ,BRAND("브랜드")
    ;

    private String option;


}

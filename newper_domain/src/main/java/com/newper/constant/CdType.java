package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CdType implements EnumOption {

    FREE("무료")
    ,PAID("유료")
    ,CONDITION("조건부 무료")
    ;

    private String option;


}

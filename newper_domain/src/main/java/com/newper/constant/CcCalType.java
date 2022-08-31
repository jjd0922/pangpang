package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 거래처 계약 정산 타입. COMPANY_CONTRACT.CC_CAL_TYPE*/
@Getter
@AllArgsConstructor
public enum CcCalType implements EnumOption {

    /**선지급*/
    EARLIER("선지급")
    /**후지급*/
    ,LATER("후지급")
    ;

    private String option;
}

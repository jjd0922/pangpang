package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 거래처 계약 상태. COMPANY_CONTRACT.CC_CAL_TYPE*/
@Getter
@AllArgsConstructor
public enum CcState implements EnumOption {

    NORMAL("정상")
    ,STOP("중지");

    private String option;



}

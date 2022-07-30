package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 거래처 계약 정산 타입. COMPANY_CONTRACT.CC_CAL_TYPE*/
@Getter
@AllArgsConstructor
public enum CcCalType {

    EARLIER("선지급")
    ,LATER("후지급");

    private String option;
}

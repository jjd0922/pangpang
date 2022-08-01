package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 거래처 계약 정산주기. COMPANY_CONTRACT.CC_CYCLE*/
@Getter
@AllArgsConstructor
public enum CcCycle {

    MONTH1("월1회")
    ,MONTH2("월2회")
    ,WEEK("주")
    ,DAY("일");

    private String option;



}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 거래처 계약 정산주기. COMPANY_CONTRACT.CC_CYCLE*/
@Getter
@AllArgsConstructor
public enum CcCycle implements EnumOption {

    /**월1회*/
    MONTH1("월1회")
    /**월2회*/
    ,MONTH2("월2회")
    /**주*/
    ,WEEK("주")
    /**일*/
    ,DAY("일")
    ;

    private String option;



}

package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 거래처 계약 구분. COMPANY_CONTRACT.CC_TYPE*/
@Getter
@AllArgsConstructor
public enum CcType {

    PURCHASE("매입")
    ,SALES("매출");

    private String option;



}

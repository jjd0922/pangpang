package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 거래처 계약 구분. COMPANY_CONTRACT.CC_FEE_TYPE*/
@Getter
@AllArgsConstructor
public enum CcFeeType implements EnumOption {

    COST("정액")
    ,RATES("정률");

    private String option;
}

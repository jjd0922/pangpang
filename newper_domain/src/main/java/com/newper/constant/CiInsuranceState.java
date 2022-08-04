package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 보증보험구분. COMPANY_INSURANCE.CI_INSURANCE_STATE */
@Getter
@AllArgsConstructor
public enum CiInsuranceState implements EnumOption {

    REQUEST("요청"),
    ISSUE("발행"),
    EXPIRED("만료");

    String option;

}

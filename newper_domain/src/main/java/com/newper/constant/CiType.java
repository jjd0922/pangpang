package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 보증보험구분. COMPANY_INSURANCE.CI_TYPE */
@Getter
@AllArgsConstructor
public enum CiType implements EnumOption {

    INSURANCE("제품사입보증보험"),
    ETC("기타");

    String option;

}

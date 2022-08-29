package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 거래처 계약 상태. COMPANY_CONTRACT.CC_CAL_TYPE*/
@Getter
@AllArgsConstructor
public enum UState implements EnumOption {

    /** 정상*/
    NORMAL("정상")
    /** 중지*/
    ,STOP("중지")
    /** 퇴사*/
    ,RETIRE("퇴사")
    ;

    private String option;

}

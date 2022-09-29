package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 본인인증요청 후 응답상태 SELF_AUTH.SA_CODE */
@Getter
@AllArgsConstructor
public enum SaCode implements EnumOption {

    BEFORE("응답전")
    ,FAIL("응답오류")
    ,SUCCESS("응답성공")
    ;

    private String option;
}

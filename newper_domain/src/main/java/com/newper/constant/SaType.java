package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 본인인증요청 구분 SELF_AUTH.SA_TYPE*/
@Getter
@AllArgsConstructor
public enum SaType implements EnumOption {

    JOIN("회원가입")
    ,FIND("정보찾기")
    ;

    private String option;

}

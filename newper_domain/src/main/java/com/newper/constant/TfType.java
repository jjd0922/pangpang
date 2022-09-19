package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**admin 문자/카카오 템플릿 구분 */
@Getter
@AllArgsConstructor
public enum TfType implements EnumOption {

    /**sms템플릿*/
    M("메세지")
    /**kakao템플릿*/
    ,K("카카오")
    ;

    private String option;

}


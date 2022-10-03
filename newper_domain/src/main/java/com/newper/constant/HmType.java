package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HmType implements EnumOption {

     BEST("", "/")
    ,TIMESALE("", "/")
    ,ETC("추가 헤더메뉴", null)

    ;

    private String option;
    /** null 아닌 경우 필수 값*/
    private String url;

    /** url이 null이 아닌 경우 필수값. return true*/
    public boolean isBasic(){
        return url != null;
    }
}

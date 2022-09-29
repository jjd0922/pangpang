package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CgsState implements EnumOption {

     BEFORE("요청전")
    ,REQ("요청")
    ,FINISH("완료")
    ;

    private String option;
}

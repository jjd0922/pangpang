package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OState implements EnumOption {

     BEFORE("입금대기")
    ,DONE("주문완료")
    ;

    private String option;
}

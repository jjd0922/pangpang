package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PnState implements EnumOption {

    REQUEST("요청"),
    OUT("공정출고"),
    COMPLETE("완료")
    ;

    private String option;
}

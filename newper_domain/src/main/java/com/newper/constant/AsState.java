package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** AFTER_SERVICE.AS_STATE*/
@Getter
@AllArgsConstructor
public enum AsState implements EnumOption {

    REQUEST("AS 요청")
    ,AS_CHECK("AS 검수완료")
    ,HOLD("보류")
    ,IMPOSSIBLE("AS 불가")
    ,PROCESS("AS 수리 완료")
    ,COMPLETE("AS 완료");

    private String option;
}
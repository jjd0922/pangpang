package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** CALCULATE_GROUP.CCG_STATE 정산상태*/
@Getter
@AllArgsConstructor
public enum OgCalConfirmState implements EnumOption {
    WAIT("정산대기")
    ,COMPLETE("정산완료")
    ,CANCEL("정산취소")
    ;
    private String option;
}

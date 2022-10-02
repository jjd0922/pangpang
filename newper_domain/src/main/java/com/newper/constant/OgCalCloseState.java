package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** CALCULATE_GROUP.CCG_CLOSE_STATE 마감상태*/
@Getter
@AllArgsConstructor
public enum OgCalCloseState implements EnumOption {
    WAIT("마감대기")
    ,COMPLETE("마감완료")
    ,CANCEL("마감취소")
    ;
    private String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** SCHEDULE.S_STATE*/
@Getter
@AllArgsConstructor
public enum SState implements EnumOption {

    /**미팅전*/
    BEFORE("미팅전"),
    /**미팅완료*/
    DONE("미팅완료"),
    /**미팅취소*/
    CANCELED("미팅취소"),
    /**미팅일정변경*/
    RESCHEDULE("미팅일정변경");

    private String option;

}

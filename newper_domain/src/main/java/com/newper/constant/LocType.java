package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 로케이션 상태*/
@Getter
@AllArgsConstructor
public enum LocType implements EnumOption {

    /**정상*/
    NORMAL("정상"),
    /**가상*/
    VIRTUAL("가상"),
    /**불용*/
    DISUSE("불용");

    private String option;
}

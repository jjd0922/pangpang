package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LocType implements EnumOption {

    NORMAL("정상"),
    VIRTUAL("가상"),
    DISUSE("불용");

    private String option;
}

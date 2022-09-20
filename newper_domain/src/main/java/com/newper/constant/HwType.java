package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**HIWORKS결재구분*/
@Getter
@AllArgsConstructor
public enum HwType implements EnumOption {

    APPROVAL("승인"),
    AGREEMENT("합의"),
    CC("참조");

    private String option;
}

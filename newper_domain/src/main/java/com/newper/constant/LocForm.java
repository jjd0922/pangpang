package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LocForm implements EnumOption {

    RACK("랙"),
    SHELF("선반"),
    FLOOR("바닥");
    
    private String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LocForm implements EnumOption {

    /**랙*/
    RACK("랙"),
    /**선반*/
    SHELF("선반"),
    /**바닥*/
    FLOOR("바닥");
    
    private String option;
}

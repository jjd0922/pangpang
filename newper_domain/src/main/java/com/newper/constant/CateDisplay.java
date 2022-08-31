package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CateDisplay implements EnumOption {

    /**노출*/
    Y("노출")
    /**미노출*/
    ,N("미노출")
    ;

    private String option;


}

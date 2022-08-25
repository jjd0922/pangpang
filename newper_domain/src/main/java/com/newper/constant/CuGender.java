package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CuGender implements EnumOption {

     M("남성")
    ,F("여성")
    ;

    private String option;
}

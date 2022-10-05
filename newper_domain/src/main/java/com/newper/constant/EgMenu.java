package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EgMenu implements EnumOption {
    IT("IT")
    ,HOME("가전")
    ,LIFE("생활")
    ;


    private String option;
}

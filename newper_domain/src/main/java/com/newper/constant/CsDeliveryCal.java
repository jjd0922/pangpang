package com.newper.constant;


import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CsDeliveryCal implements EnumOption {

    INCLUDE("포함")
    ,EXCEPT("미포함");

    private String option;
}

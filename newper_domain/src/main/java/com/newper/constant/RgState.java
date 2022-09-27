package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**RESELL.RG_STATE 반품상태*/
@Getter
@AllArgsConstructor
public enum RgState implements EnumOption {

    REQ("반품요청"),
    ING("반품진행중"),
    COMPLETE("반품완료");

    private String option;

}
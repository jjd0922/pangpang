package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/** CHECK_GROUP.CG_TYPE 검수 타입 */
public enum CgType implements EnumOption {

    IN("입고검수"),
    RE("재검수"),
    OUT("출고검수");

    private String option;
}

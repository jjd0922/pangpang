package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/** CHECK_GOODS.CGS_TYPE 검수 타입 */
public enum CgsType implements EnumOption {

    IN("입고검수"),
    RE("재검수"),
    AS("AS검수"),
    OUT("출고검수");

    private String option;
}

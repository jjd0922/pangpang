package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 견적서 상태. PO_ESTIMATE.EM_STATE*/
@Getter
@AllArgsConstructor
public enum PeState implements EnumOption {
    AVAILABLE("유효")
    ,DISPOSAL("폐기");

    private String option;
}

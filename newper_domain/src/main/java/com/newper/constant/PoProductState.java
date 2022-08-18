package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 발주품의 상태. PO.PO_BUY_PRODUCT_STATE*/
@Getter
@AllArgsConstructor
public enum PoProductState implements EnumOption {
    NORMAL("정상품")
    ,REFURB("리퍼상품")
    ,TOTAL("입점상품(총액)")
    ,NET("입점상품(순액)")
    ,AS("AS")
    ,SERVICE("용역");

    private String option;
}

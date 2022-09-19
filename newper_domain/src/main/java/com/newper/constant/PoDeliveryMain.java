package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/** PO 매입운송비 주체 PO.PO_DELIVERY_MAIN */
public enum PoDeliveryMain {

    MAIN("본사"),
    PARTNER("거래처");

    private String option;

}
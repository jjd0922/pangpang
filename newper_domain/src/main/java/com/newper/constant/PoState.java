package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 발주품의 상태. PO.PO_STATE*/
@Getter
@AllArgsConstructor
public enum PoState implements EnumOption {
    WAITING("승인대기")
    ,APPLY("승인요청")
    ,REFER("승인반려")
    ,APPROVAL("승인완료")
    ,URGENCY("긴급발주");

    private String option;
}

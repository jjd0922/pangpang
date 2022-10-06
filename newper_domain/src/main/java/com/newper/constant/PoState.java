package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 발주품의 상태. PO.PO_STATE*/
@Getter
@AllArgsConstructor
public enum PoState implements EnumOption {


     WAITING("발주전")
    ,CANCEL("발주취소")
    ,COMPLETE("발주완료")

    ;

    private String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 발주품의 상태. PO.PO_STATE*/
@Getter
@AllArgsConstructor
public enum PoState implements EnumOption {
    /**발주대기*/
    WAITING("발주대기")
    /**발주취소*/
    ,CANCEL("발주취소")
    /**발주완료*/
    ,APPROVAL("발주완료")
    ;

    private String option;
}

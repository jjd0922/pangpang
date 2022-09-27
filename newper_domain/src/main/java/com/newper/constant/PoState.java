package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 발주품의 상태. PO.PO_STATE*/
@Getter
@AllArgsConstructor
public enum PoState implements EnumOption {


    WAITING("발주대기")
    ,REQUEST("승인요청")
    ,REJECT("승인반려")
    ,APPROVAL("승인완료")
    ,CANCEL("발주취소")
    ,COMPLETE("발주완료")

    ,RE_REQUEST("재승인요청")
    ,RE_APPROVAL("재승인완료")
    ,RE_REJECT("재승인반려")
    ;

    private String option;
}

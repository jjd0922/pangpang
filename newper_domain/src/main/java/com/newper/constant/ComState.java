package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** 거래처 상태. COMPANY.COM_STATE*/
@Getter
@AllArgsConstructor
public enum ComState {

    NORMAL("정상업체")
    ,STOP("거래중지")
    ,ETC("기타");

    private String option;



}

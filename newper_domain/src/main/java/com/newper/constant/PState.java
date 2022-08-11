package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 상품 상태*/
@Getter
@AllArgsConstructor
public enum PState {

     PROTO("등록대기")
    ,SELL("판매")
    ,STOP("판매중지")
    ,END("폐기")
    ;

    String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 상품 상태*/
@Getter
@AllArgsConstructor
public enum PState implements EnumOption {


    /**등록대기*/
    PROTO("등록대기")
    /**승인요청*/
    ,REQUEST("승인요청")
    /**승인반려*/
    ,REFUSAL("승인반려")
    /**판매*/
    ,SELL("승인=판매")
    /**판매중지*/
    ,STOP("판매중지")
    /**폐기*/
    ,END("폐기")
    ;

    private String option;
}

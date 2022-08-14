package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 상품 상태*/
@Getter
@AllArgsConstructor
public enum PState implements EnumOption {

     PROTO("등록대기")
    ,REQUEST("승인요청")
    ,REFUSAL("승인반려")
    ,SELL("승인=판매")
    ,STOP("판매중지")
    ,END("폐기")
    ;

    private String option;
}

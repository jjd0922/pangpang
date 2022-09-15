package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
/** FAQ 타입 FAQ.FAQ_TYPE*/
@Getter
@AllArgsConstructor
public enum FaqType implements EnumOption {
    COMMON("공통")
    ,CUSTOMER("회원")
    ,ORDER("주문/결제확인")
    ,TRADE("교환/환불/취소")
    ,CONFIRM("보상/회수/접수확인")
    ,DELIVERY("배송문의")
    ,RECEIPT("영수증/증빙")
    ,PRODUCT("상품문의")
    ,DUTY("세금계산서 발행문의")
    ;
    private String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QspType implements EnumOption {

    PERFORMANCE_N_SPEC("성능 및 스펙"),
    BUY_N_RECOMMEND("상품구매 및 상품추천"),
    PRICE("가겨문의"),
    PAYMENT("결제 및 입금관련"),
    DELIVERY("배송문의"),
    EVENT_N_PERIOD("할인이벤트 및 기간연장"),
    BULK_ORDER("대량주문"),
    COMPENSATION("보상문의"),
    ETC("기타문의");

    private String option;
}

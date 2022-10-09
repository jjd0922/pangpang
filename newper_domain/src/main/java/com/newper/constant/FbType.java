package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FbType implements EnumOption {
    RECENT("최근본상품")
    ,BASKET("장바구니")
    ,TEL("상담전화")
    ,ETC("기타")
    ;


    private String option;
}


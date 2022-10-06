package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 1:1문의 문의타입(상세) QNA.QNA_TYPE2*/
@Getter
@AllArgsConstructor
public enum QnaType2 implements EnumOption {
    RECEIPT_CASH("현금영수증", QnaType1.RECEIPT); // 내용 확인 후 추가 필요

    private String option;
    private QnaType1 qnaType1;
}

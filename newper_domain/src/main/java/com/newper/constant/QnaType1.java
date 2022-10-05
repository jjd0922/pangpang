package com.newper.constant;


import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 1:1문의 문의타입 QNA.QNA_TYPE1 */
@Getter
@AllArgsConstructor
public enum QnaType1 implements EnumOption {
    RECEIPT("영수증/증빙");

    private String option;
}

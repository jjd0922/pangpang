package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 설치 / 배송 상태.DN_STATE*/
@Getter
@AllArgsConstructor
public enum DnState implements EnumOption {

    REQUEST("요청"),
    ING("배송/설치중"),
    COMPLETE("완료");

    private String option;
}
package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**상품권그룹상태*/
@Getter
@AllArgsConstructor
public enum GiftgState implements EnumOption {

    WAITING("등록대기"),
    REQUEST("승인요청"),
    REFUSAL("승인반려"),
    APPROVAL("승인");

    private String option;
}

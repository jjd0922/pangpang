package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**쿠폰그룹상태*/
@Getter
@AllArgsConstructor
public enum CpgState implements EnumOption {

    /**대기*/
    WAITING("대기"),
    /**사용중*/
    USING("사용중"),
    /**소진*/
    END("소진"),
    /**폐기*/
    DISPOSE("폐기");

    private String option;
}
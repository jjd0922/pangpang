package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**회원상태 customer.CU_STATE*/
@Getter
@AllArgsConstructor
public enum CuState implements EnumOption {

    /**정상회원*/
    NORMAL("정상회원"),
    /**휴면회원*/
    SLEEP("휴면회원"),
    /**탈퇴회원*/
    STOP("탈퇴회원");

    private String option;

}

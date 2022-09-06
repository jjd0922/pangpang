package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**쿠폰 타쿠폰과 중복사용가능한지*/
@Getter
@AllArgsConstructor
public enum CpgDuplicate implements EnumOption {

    /**중복사용가능*/
    Y("중복사용가능"),
    /**중복사용불가*/
    N("중복사용불가");

    private String option;
}

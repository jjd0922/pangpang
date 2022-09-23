package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PDelCompany implements EnumOption {
    MALL("전시몰"),
    SUPPLY("공급업체"),

    ;

    private String option;
}

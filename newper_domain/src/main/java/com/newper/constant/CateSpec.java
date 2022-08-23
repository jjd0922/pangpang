package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** CATEGORY.CATE_SPEC 스펙 사용여부*/
@Getter
@AllArgsConstructor
public enum CateSpec implements EnumOption {

     Y("사용")
    ,N("미사용")
    ;

    private String option;
}

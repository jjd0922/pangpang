package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** CATEGORY.CATE_SPEC 스펙 사용여부*/
@Getter
@AllArgsConstructor
public enum CateSpec {

     Y("사용")
    ,N("미사용")
    ;

    private String option;
}

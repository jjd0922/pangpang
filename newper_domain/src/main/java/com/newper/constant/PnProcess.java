package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/** PROCESS_NEED.PN_PROCESS 영업검수 결과 공정 진행 여부*/
public enum PnProcess implements EnumOption {

    BEFORE("영업 검수전")
    ,N("미진행")
    ,Y("진행")
    ;

    private String option;
}

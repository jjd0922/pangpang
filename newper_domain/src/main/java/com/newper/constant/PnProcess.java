package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/** PROCESS_NEED.PN_PROCESS 영업검수 결과 공정 진행 여부*/
public enum PnProcess {
    BEFORE("영업 검수전")
    ,N("공정 미진행")
    ,Y("공정 진행")
    ;

    private String option;
}

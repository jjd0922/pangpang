package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**사업자 분류(거래처의 사업형태)*/
public enum ComType {

    INDIVIDUAL("개인")
    ,BUSINESSMAN("개인사업자")
    ,CORPORATION("법인");

    private String option;

}

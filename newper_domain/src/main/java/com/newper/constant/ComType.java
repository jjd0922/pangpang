package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**사업자 분류(거래처의 사업형태)*/
public enum ComType {

    INDIV("개인")
    ,BMAN("개인사업자")
    ,CORPO("법인");

    private String option;

}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** CALCULATE_GROUP.CCG_TYPE*/
@Getter
@AllArgsConstructor
public enum CcgType implements EnumOption {

//    정산타입(매입처, 입점사, 공정업체, PG, 배송, 설치)

    PURCHASE("매입처"),
    STORE("입점사"),
    PROCESS("외주가공"),
    PG("PG"),
    DELIVERY("배송"),
    INSTALL("설치")
    ;

    private String option;



}

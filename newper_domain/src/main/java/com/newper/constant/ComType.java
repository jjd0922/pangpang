package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**사업자 분류(거래처의 사업형태)*/
public enum ComType implements EnumOption {

    /**개인*/
    INDIV("개인")
    /**개인사업자*/
    ,BMAN("개인사업자")
    /**법인*/
    ,CORPO("법인")
    ;

    private String option;

}

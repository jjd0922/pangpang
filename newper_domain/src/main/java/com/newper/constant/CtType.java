package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**거래처 구분코드(거래처의 유형)*/
public enum CtType implements EnumOption {

    MAIN("본사")
    ,PURCHASE("매입처")
    ,SELL("판매처")
    ,STORE("입점사")
    ,RECOATING("도색업체")
    ,REPAIRING("수리업체")
    ,DELIVERY("배송업체")
    ,SETTING("설치업체")
    ,ETC("기타")
    ;

    private String option;

}

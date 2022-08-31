package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**거래처 구분코드(거래처의 유형)*/
public enum CtType implements EnumOption {

    /**본사*/
    MAIN("본사")
    /**매입처*/
    ,PURCHASE("매입처")
    /**판매처*/
    ,SELL("판매처")
    /**입점사*/
    ,STORE("입점사")
    /**도색업체*/
    ,RECOATING("도색업체")
    /**수리업체*/
    ,REPAIRING("수리업체")
    /**배송업체*/
    ,DELIVERY("배송업체")
    /**설치업체*/
    ,SETTING("설치업체")
    /**기타*/
    ,ETC("기타")
    ;

    private String option;

}

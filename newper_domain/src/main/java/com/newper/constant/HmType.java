package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HmType implements EnumOption {

    ETC("추가 GNB 메뉴", null)
    ,NEW("신상입고", "/newLaunch")
    ,BEST("베스트100", "/best")
    ,TIMESALE("타임세일", "/timeSale")
    ,LAST("품절임박", "/lastSecond")
    ,EVENT("이벤트/기획전", "/event")
    ,REVIEW("상품리뷰", "/review")

    ;

    private String option;
    /** null 아닌 경우 필수 값*/
    private String url;

    /** url이 null이 아닌 경우 필수값. return true*/
    public boolean isBasic(){
        return url != null;
    }
}

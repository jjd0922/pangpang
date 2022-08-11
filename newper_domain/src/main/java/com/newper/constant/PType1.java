package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 정산구분(정산구분1) PRODUCT.P_TYPE1 */
@Getter
@AllArgsConstructor
public enum PType1 {

     NORMAL("정상품")
    ,REFUR("리퍼상품")
    ;
    private String option;

}

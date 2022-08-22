package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 정산구분(품목구분1) PRODUCT.P_TYPE1, PO.PO_BUY_PRODUCT_TYPE */
@Getter
@AllArgsConstructor
public enum PType1 implements EnumOption {

     NORMAL("정상품")
    ,REFUR("리퍼상품")
    ,TOTALCOST("입점상품(총액)")
    ,NETAMOUNT("입점상품(순액)")
    ,AS("AS")
    ,SERVICE("용역")
    ;
    private String option;

}

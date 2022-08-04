package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 마스킹 table column list. user.u_mask*/
@Getter
@AllArgsConstructor
public enum AuthMask {

    CU_PHONE("CUSTOMER","CU_PHONE", AuthMask.TYPE_PHONE)
    ,CU_NAME("CUSTOMER","CU_NAME", AuthMask.TYPE_NAME)
    ,ADDRESS_ADDR4("ADDRESS","ADDR4", AuthMask.TYPE_ADDRESS)

    ;

    private String table;
    private String column;
    /** 마스킹 구분*/
    private String type;

    public static final String TYPE_PHONE = "PHONE";
    public static final String TYPE_NAME = "NAME";
    public static final String TYPE_ADDRESS = "ADDRESS";

}

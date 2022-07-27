package com.newper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 마스킹 table column list. user.u_mask*/
@Getter
@AllArgsConstructor
public enum UMask {

    CU_PHONE("CUSTOMER","CU_PHONE", UMask.TYPE_PHONE)
    ,CU_NAME("CUSTOMER","CU_NAME", UMask.TYPE_NAME)
    ,ADDRESS_ADDR4("ADDRESS","ADDR4", UMask.TYPE_ADDRESS)

    ;

    private String table;
    private String column;
    /** 마스킹 구분*/
    private String type;

    public static final String TYPE_PHONE = "PHONE";
    public static final String TYPE_NAME = "NAME";
    public static final String TYPE_ADDRESS = "ADDRESS";



}

package com.newper.constant.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 마스킹 table column list. user.u_mask*/
@Getter
@AllArgsConstructor
public enum AuthMask {

    CU_PHONE("CUSTOMER","CU_PHONE", AuthMask.TYPE_PHONE, "개인정보", "휴대폰 번호")
    ,CU_NAME("CUSTOMER","CU_NAME", AuthMask.TYPE_NAME, "개인정보", "고객명")
    ,ADDRESS_ADDR4("ADDRESS","ADDR4", AuthMask.TYPE_ADDRESS, "개인정보 테스트", "주소")

    ;


    public static final String TYPE_PHONE = "PHONE";
    public static final String TYPE_NAME = "NAME";
    public static final String TYPE_ADDRESS = "ADDRESS";

    private String table;
    private String column;
    /** 마스킹 구분*/
    private String type;

    private String optionGroup;
    private String option;


}

package com.newper.dto;

import com.newper.constant.etc.IamPortPayMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/** 결제 준비 DTO*/
@Getter
@Setter
public class IamportReq {

    private String pg;
    private IamPortPayMethod pay_method;
    private boolean escrow;
    private String merchant_uid;
    private String name;
    private int amount;
    private Map<String,Object> custom_data;
    private int tax_free;
    private String currency = "KRW";
    private String language = "ko";
    private String buyer_name;
    private String buyer_tel;
    private String buyer_email;
    private String buyer_addr;
    private String buyer_postcode;
    private String confirm_url;
    private String notice_url;
    private Map<String, Object> display;

    /** 필수항목 생성자*/
    public IamportReq(IamPortPayMethod pay_method, String merchant_uid, int amount, String buyer_tel) {
        this.pay_method = pay_method;
        this.merchant_uid = merchant_uid;
        this.amount = amount;
        this.buyer_tel = buyer_tel;
    }

}

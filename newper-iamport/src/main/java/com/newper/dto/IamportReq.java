package com.newper.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/** 결제 준비 DTO*/
@Getter
public class IamportReq {

    private String pg;
    private String pay_method;
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

    /** merchant_uid. ph_idx 사용*/
    public IamportReq(long ph_idx){
        this.merchant_uid = "ph"+ph_idx;
    }

    /** NHN KCP 필요 정보들*/
    public void setKcpInfo(String mid,
                           String pay_method,
                           String name,
                           int amount,
                           String buyer_email,
                           String buyer_name,
                           String buyer_tel,
                           String buyer_addr,
                           String buyer_postcode) {
        //사이트코드 테스트인경우 kcp.T000
        this.pg = "kcp."+mid;
        this.pay_method = pay_method;
        this.name = name;
        this.amount = amount;
        this.buyer_email = buyer_email;
        this.buyer_name = buyer_name;
        this.buyer_tel = buyer_tel;
        this.buyer_addr = buyer_addr;
        this.buyer_postcode = buyer_postcode;
//        this.language = language;
//        this.m_redirect_url = m_redirect_url;
    }
}

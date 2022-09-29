package com.newper.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/** 결제 준비 DTO*/
@Getter
@Setter
public class IamportReq {

    private String pg;
    private String pay_method;
    private Boolean escrow;
    private String merchant_uid;
    private String name;
    private int amount;
    private Map<String,Object> custom_data;
    private Integer tax_free;
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


    /** yyyyMMdd. naver pay 이용 완료일 . 결제 당일 or 미래의 일자*/
    private String naverUseCfm;
    private Boolean naverPopupMode;
    /** 구매자명*/
    private String naverPurchaserName;

//    분양몰 도메인마다 다르게 들어가야하므로 js에서 처리하고 있음
//    private String m_redirect_url;

    /** merchant_uid. ph_idx 사용*/
    public IamportReq(long ph_idx, String pg, int amount, String name){
        this.merchant_uid = "ph"+ph_idx;
        this.pg = pg;
        this.amount = amount;
        this.name = name;
    }

    /** NHN KCP 필요 정보들*/
    public void setKcp(
                           String pay_method,
                           String buyer_email,
                           String buyer_name,
                           String buyer_tel,
                           String buyer_addr,
                           String buyer_postcode) {
        this.pay_method = pay_method;
        this.buyer_email = buyer_email;
        this.buyer_name = buyer_name;
        this.buyer_tel = buyer_tel;
        this.buyer_addr = buyer_addr;
        this.buyer_postcode = buyer_postcode;
//        this.language = language;
    }
    /** 네이버 페이 필요 정보들*/
    public void setNaverpay(
//            String name,
            String buyer_name) {
        this.naverUseCfm = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.naverPopupMode = false;
        this.naverPurchaserName = buyer_name;

        //네이버페이 내부적으로 외 2개 의 표현이 자동생성되므로 "xxxx 외 2개" 대신naverProducts[0].name(첫번째 상품명)으로 설정하길 권장합니다.
//        this.name = name;
    }
    /** 페이코 필요 정보들*/
    public void setKakaopay(){
    }
    /** 페이코 필요 정보들*/
    public void setPayco(String buyer_tel){

        this.buyer_tel = buyer_tel;
    }
    /** 차이 간편 결제*/
    public void setChai(String buyer_name){
        this.buyer_name = buyer_name;
        this.pay_method = "trans";
    }
}

package com.newper.constant.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamPortPayMethod {

    CARD("신용카드", true)
    ,TRANS("실시간계좌이체", true)
    ,VBANK("가상계좌", true)
    ,PHONE("휴대폰소액결제", true)
    ,SAMSUNG("삼성페이 / 이니시스, KCP 전용", false)
    ,KPAY("KPay앱 직접호출 / 이니시스 전용", false)
    ,KAKAOPAY("카카오페이 직접호출 / 이니시스, KCP, 나이스페이먼츠 전용", false)
    ,PAYCO("페이코 직접호출 / 이니시스, KCP 전용", false)
    ,LPAY("LPAY 직접호출 / 이니시스 전용", false)
    ,SSGPAY("SSG페이 직접호출 / 이니시스 전용", false)
    ,TOSSPAY("토스간편결제 직접호출 / 이니시스 전용", false)
    ,CULTURELAND("문화상품권 / 이니시스, 토스페이먼츠(구 LG U+), KCP 전용", false)
    ,SMARTCULTURE("스마트문상 / 이니시스, 토스페이먼츠(구 LG U+), KCP 전용", false)
    ,HAPPYMONEY("해피머니 / 이니시스, KCP 전용", false)
    ,BOOKNLIFE("도서문화상품권 / 토스페이먼츠(구 LG U+), KCP 전용", false)
    ,POINT("베네피아 포인트 등 포인트 결제 / KCP 전용", false)
    ,WECHAT("위쳇페이 / 엑심베이 전용", false)
    ,ALIPAY("알리페이 / 엑심베이 전용", false)
    ,UNIONPAY("유니온페이 / 엑심베이 전용", false)
    ,TENPAY("텐페이 / 엑심베이 전용", false)
    ;

    private String option;
    private boolean common;
}

package com.newper.constant.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamPortPg {

//     HTML5_INICIS("이니시스웹표준")
//    ,INICIS("이니시스ActiveX결제창")
     KCP("NHN KCP")
//    ,KCP_BILLING("NHN KCP 정기결제")
    ,UPLUS("토스페이먼츠(구 LG U+)")
//    ,NICE("나이스페이")
//    ,JTNET("JTNet")
//    ,KICC("한국정보통신")
//    ,BLUEWALNUT("블루월넛")
    ,KAKAOPAY("카카오페이")
//    ,DANAL("다날휴대폰소액결제")
//    ,DANAL_TPAY("다날일반결제")
//    ,MOBILIANS("모빌리언스 휴대폰소액결제")
    ,CHAI("차이 간편결제")
//    ,SYRUP("시럽페이")
    ,PAYCO("페이코")
//    ,PAYPAL("페이팔")
//    ,EXIMBAY("엑심베이")
    ,NAVERPAY("네이버페이-결제형")
//    ,NAVERCO("네이버페이-주문형")
//    ,SMILEPAY("스마일페이")
//    ,ALIPAY("알리페이")
//    ,PAYMENTWALL("페이먼트월")
//    ,PAYPLE("페이플")
//    ,TOSSPAY("토스간편결제")
//    ,SMARTRO("스마트로")
//    ,SETTLE("세틀뱅크")
    ;

    private String option;

    /** iamport 보낼 때는 소문자로 세팅*/
    public String getPg(){
        return this.name().toLowerCase();
    }

}

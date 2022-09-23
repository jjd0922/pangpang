package com.newper.component;

import com.newper.exception.MsgException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/** 나이스 본인인증 관련 class*/
@Component
@Slf4j
public class NiceApi {

    @Value("${nice.auth.sitecode}")
    private String auth_sitecode;
    @Value("${nice.auth.pw}")
    private String auth_pw;

    /** 본인 인증 요청 팝업 띄우기 전. key = sRequestNumber, sEncData */
    public Map<String,Object> getNiceSendData(String domain) {
        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

        String sRequestNumber = niceCheck.getRequestNO(auth_sitecode);        	// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로

        String sAuthType = "";      	// 없으면 기본 선택화면, M(휴대폰), X(인증서공통), U(공동인증서), F(금융인증서), S(PASS인증서), C(신용카드)
        String customize 	= "";		//없으면 기본 웹페이지 / Mobile : 모바일페이지

        // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
        //리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
        String sReturnUrl = domain+"/customer/auth/response";      // 성공시 이동될 URL
        String sErrorUrl = "localhost:9200/customer/error.ajax";          // 실패시 이동될 URL

        // 입력될 plain 데이타를 만든다.
        String sPlainData =
                "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                        "8:SITECODE" + auth_sitecode.getBytes().length + ":" + auth_sitecode +
                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                        "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize;

        String sMessage = null;
        String sEncData = "";

        int iReturn = niceCheck.fnEncode(auth_sitecode, auth_pw, sPlainData);
        if( iReturn == 0 ) {
            sEncData = niceCheck.getCipherData();
        } else if( iReturn == -1) {
            sMessage = "암호화 시스템 에러입니다.";
        } else if( iReturn == -2) {
            sMessage = "암호화 처리오류입니다.";
        } else if( iReturn == -3) {
            sMessage = "암호화 데이터 오류입니다.";
        } else if( iReturn == -9) {
            sMessage = "입력 데이터 오류입니다.";
        } else {
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }
        if(sMessage != null){
            log.error("NICE 본인인증 요청전 중 에러 발생");
            throw new MsgException("잠시 후 다시 시도해주세요");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sRequestNumber",sRequestNumber);
        map.put("sEncData",sEncData);
        return map;
    }
}

package com.newper.component;

import com.newper.dto.ParamMap;
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
//        String sErrorUrl = "localhost:9200/customer/error.ajax";          // 실패시 이동될 URL

        // 입력될 plain 데이타를 만든다.
        String sPlainData =
                "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                        "8:SITECODE" + auth_sitecode.getBytes().length + ":" + auth_sitecode +
                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
//                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
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

    /** nice 응답 후 받아온 데이터 return
     * <pre>
     * 조회시 key : value
     * REQ_SEQ : NICE본인인증 요청번호
     * AUTH_TYPE : 인증수단 (M:휴대폰 C:카드 X:인증서 P:삼성패스)
     * RES_SEQ : 처리결과 고유번호
     * NAME : 이름
     * UTF8_NAME : 이름 (UTF-8)
     * BIRTHDATE : 생년월일(YYYYMMDD)
     * GENDER : 성별코드 (0:여성 1:남성)
     * NATIONALINFO : 내/외국인코드 (0:내국인 1:외국인)
     * DI : DI(중복가입확인값)
     * MOBILE_CO : 통신사
     * MOBILE_NO : 휴대폰번호
     * </pre>*/
    public Map<String,Object> getNiceReturn(ParamMap paramMap) {
        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

        String sEncodeData = requestReplace(paramMap.getString("EncodeData"), "encodeData");

        String sCipherTime = "";			// 복호화한 시간
        String sMessage = "";
        String sPlainData = "";

        int iReturn = niceCheck.fnDecode(auth_sitecode, auth_pw, sEncodeData);

        if( iReturn == 0 ) {
            sPlainData = niceCheck.getPlainData();
            sCipherTime = niceCheck.getCipherDateTime();

            // 데이타를 추출합니다.
            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

            /*String session_sRequestNumber = (String)shopSession.getAttribute("REQ_SEQ");
            if(!sRequestNumber.equals(session_sRequestNumber))
            {
                sMessage = "세션값 불일치 오류입니다.";
                sResponseNumber = "";
                sAuthType = "";
            }*/
            return mapresult;
        } else if( iReturn == -1) {
            sMessage = "복호화 시스템 오류입니다.";
        } else if( iReturn == -4) {
            sMessage = "복호화 처리 오류입니다.";
        } else if( iReturn == -5) {
            sMessage = "복호화 해쉬 오류입니다.";
        } else if( iReturn == -6) {
            sMessage = "복호화 데이터 오류입니다.";
        } else if( iReturn == -9) {
            sMessage = "입력 데이터 오류입니다.";
        } else if( iReturn == -12) {
            sMessage = "사이트 패스워드 오류입니다.";
        } else {
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }
        if(sMessage != null){
            log.error("NICE 본인인증 응답 중 에러 발생");
            throw new MsgException("잠시 후 다시 시도해주세요");
        }
        return null;
    }

    public String requestReplace (String paramValue, String gubun) {
        String result = "";

        if (paramValue != null) {

            paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            paramValue = paramValue.replaceAll("\\*", "");
            paramValue = paramValue.replaceAll("\\?", "");
            paramValue = paramValue.replaceAll("\\[", "");
            paramValue = paramValue.replaceAll("\\{", "");
            paramValue = paramValue.replaceAll("\\(", "");
            paramValue = paramValue.replaceAll("\\)", "");
            paramValue = paramValue.replaceAll("\\^", "");
            paramValue = paramValue.replaceAll("\\$", "");
            paramValue = paramValue.replaceAll("'", "");
            paramValue = paramValue.replaceAll("@", "");
            paramValue = paramValue.replaceAll("%", "");
            paramValue = paramValue.replaceAll(";", "");
            paramValue = paramValue.replaceAll(":", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll("#", "");
            paramValue = paramValue.replaceAll("--", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll(",", "");

            if(gubun != "encodeData"){
                paramValue = paramValue.replaceAll("\\+", "");
                paramValue = paramValue.replaceAll("/", "");
                paramValue = paramValue.replaceAll("=", "");
            }

            result = paramValue;

        }
        return result;
    }

}

package com.newper.component;

import com.newper.dto.ParamMap;
import com.newper.util.CommonRestApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class NaverLogin extends CommonRestApi {

    @Value("${naver.client.id}")
    private String client_id;
    @Value("${naver.client.secret}")
    private String client_secret;

    /** 네이버 로그인 api 요청정보 map 생성*/
    public Map<String,Object> request(String domain) {
        String redirect_uri = domain+"/customer/auth/naver/response";
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        System.out.println("client_id = " + client_id);
        Map<String,Object> map = new HashMap<>();
        map.put("client_id", client_id);
        map.put("redirect_uri", redirect_uri);
        map.put("state", state);
        return map;
    }

    /** 네이버 로그인 접속토큰 return
     * <pre>
     * 조회 key : value
     * ---- 정상
     * access_token : 접근 토큰, 발급 후 expires_in 파라미터에 설정된 시간(초)이 지나면 만료됨
     * refresh_token : 갱신 토큰, 접근 토큰이 만료될 경우 접근 토큰을 다시 발급받을 때 사용
     * token_type : 접근 토큰의 타입으로 Bearer와 MAC의 두 가지를 지원
     * expires_in : 접근 토큰의 유효 기간(초 단위)
     * </pre>
     * <pre>
     * ---- 에러
     * error : 에러 코드
     * error_description : 에러 메시지
     * </pre>
     * */
    public Map<String,Object> getToken(ParamMap paramMap, String domain) {
        String redirect_uri = domain + "/customer/auth/naver/response";
        String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code" +
                "&client_id=" + client_id +
                "&client_secret=" + client_secret +
                "&redirect_uri=" + redirect_uri +
                "&code=" + paramMap.getString("code") +
                "&state=" + paramMap.getString("state");
        HttpURLConnection con = connect(apiURL);
        String result = "";
        try {
            con.setRequestMethod("GET");
            return getResponseMap(con);
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    /** 네이버 로그인 회원 프로필 return
     * <pre>
     * resultcode : API 호출 결과 코드
     * message : 호출 결과 메시지
     * response : 회원 profile 정보 map
     * </pre>
     * <pre>
     * ---- response key:value
     * id : 동일인 식별 정보
     * nickname : 사용자 별명
     * name : 사용자 이름
     * email : 사용자 메일 주소
     * gender : 성별 (F:여성 M:남성 U:확인불가)
     * age : 사용자 연령대
     * birthday : 사용자 생일(MM-DD)
     * profile_image : 사용자 프로필사진 url
     * birthyear : 출생연도
     * mobile : 휴대전화번호
     * </pre>
     * */
    public Map<String,Object> getProfile(ParamMap paramMap, String domain) {
        Map<String, Object> token = getToken(paramMap, domain);
        String header_auth = token.get("token_type").toString() + " " + token.get("access_token");
        HttpURLConnection con = connect("https://openapi.naver.com/v1/nid/me");
        try {
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header_auth);
            return getResponseMap(con);
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

}

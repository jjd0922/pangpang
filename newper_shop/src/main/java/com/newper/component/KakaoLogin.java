package com.newper.component;

import com.newper.dto.ParamMap;
import com.newper.util.CommonRestApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class KakaoLogin extends CommonRestApi {

    @Value("${kakao.client.id}")
    private String client_id;

    @Value("${kakao.client.secret}")
    private String client_secret;

    private String redirect_uri;

    /** 카카오 로그인 요청정보 map 생성*/
    public Map<String, Object> request(String domain) {
        redirect_uri = domain+"/customer/auth/kakao/response";
        Map<String,Object> map = new HashMap<>();
        map.put("client_id",client_id);
        map.put("redirect_uri",redirect_uri);
        return map;
    }

    /** 카카오 로그인 토큰 return
     * <pre>
     * 조회 key : value
     * token_type : 토큰타입, bearer로 고정
     * access_token : 사용자 액세스 토큰 값
     * id_token : ID 토큰 값(OpenID Connect 확장 기능을 통해 발급되는 ID 토큰, Base64 인코딩 된 사용자 인증 정보 포함)
     * expires_in : 액세스/ID 토큰 만료시간(초)
     * refresh_token : 사용자 리프레시 토큰 값
     * refresh_token_expires_in : 리프레시 토큰 만료시간(초)
     * scope : 인증된 사용자의 정보 조회 권한 범위(여러개의 경우 공백으로 구분)
     *         OpenID Connect가 활성화된 앱의 토큰 발급 요청인 경우, ID 토큰이 함께 발급되며 scope 값에 openid 포함
     * </pre> */
    public Map<String, Object> getToken(ParamMap paramMap, String domain) {
        redirect_uri = domain+"/customer/auth/kakao/response";
        String apiUrl = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code" +
                "&client_id=" + client_id +
                "&redirect_uri=" + redirect_uri +
                "&code=" + paramMap.getString("code") +
                "&client_secret=" + client_secret;
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("POST");
            Map<String, Object> responseMap = getResponseMap(con);
            return responseMap;
        } catch (Exception e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    /** 카카오 로그인 response map*/
    public Map<String,Object> getProfile(ParamMap paramMap, String domain) {
        Map<String, Object> token = getToken(paramMap, domain);
        String header_value = token.get("token_type").toString() + " " + token.get("access_token").toString();
        String apiUrl = "https://kapi.kakao.com/v1/oidc/userinfo";
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header_value);
            return getResponseMap(con);
        } catch (Exception e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

}

package com.newper.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newper.dto.ParamMap;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Getter
@Setter
public class NaverLogin {

//    String clientId = "ckW6gv_5uIKK7mfPoWnI";
    String clientId = "3ZXdfgg0tFViCONIPRQE"; // 개발용 임시
    String clientSecret = "h_VBw2ZOeh";
    String redirectURI;
    String state;
    String accessToken = "";
    String refresh_token = "";

    /** 네이버 로그인 api 요청정보 map 생성*/
    public Map<String,Object> request(String domain) {
        setRedirectURI(domain+"/customer/auth/naver/access");
        SecureRandom random = new SecureRandom();
        setState(new BigInteger(130, random).toString());
        Map<String,Object> map = new HashMap<>();
        map.put("clientId", getClientId());
        map.put("redirectURI", getRedirectURI());
        map.put("state", getState());
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
    public Map<String,Object> access(ParamMap paramMap, String domain) {
        redirectURI = domain + "/customer/auth/naver/response";
        String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectURI +
                "&code=" + paramMap.getString("code") +
                "&state=" + paramMap.getString("state");
        HttpURLConnection con = connect(apiURL);
        try {
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuilder res = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            if (responseCode == 200) {
                ObjectMapper om = new ObjectMapper();
                Map<String, Object> map = om.readValue(res.toString(), new TypeReference<Map<String, Object>>() {});
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        Map<String, Object> access = access(paramMap, domain);
        String header_auth = access.get("token_type").toString() + " " + access.get("access_token");
        HttpURLConnection con = connect("https://openapi.naver.com/v1/nid/me");
        Map<String,String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header_auth);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            ObjectMapper om = new ObjectMapper();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String result = readBody(con.getInputStream());
                Map<String, Object> map = om.readValue(result, new TypeReference<Map<String, Object>>() {});
                return map;
            } else {
                String result = readBody(con.getErrorStream());
                Map<String, Object> map = om.readValue(result, new TypeReference<Map<String, Object>>() {});
                return map;
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    /** httpUrlConnect*/
    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    /** api responseBody 응답 읽기*/
    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader linReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = linReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

}

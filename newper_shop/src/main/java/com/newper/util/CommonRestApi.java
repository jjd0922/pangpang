package com.newper.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class CommonRestApi {
    /** httpUrlConnect*/
    protected static HttpURLConnection connect(String apiUrl) {
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
    protected static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    /** 응답값 map형식으로 return */
    protected Map<String, Object> getResponseMap(HttpURLConnection con) throws IOException {
        int responseCode = con.getResponseCode();
        String result = "";
        if (responseCode == HttpURLConnection.HTTP_OK) {
            result = readBody(con.getInputStream());
        } else {
            result = readBody(con.getErrorStream());
        }
        ObjectMapper om = new ObjectMapper();
        Map<String, Object> map = om.readValue(result, new TypeReference<Map<String, Object>>() {});
        return map;
    }
}

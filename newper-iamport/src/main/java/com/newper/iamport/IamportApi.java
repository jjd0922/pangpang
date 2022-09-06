package com.newper.iamport;

import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/** iamport 통신 클래스*/
public class IamportApi {

    private static OkHttpClient CLIENT = null;
    private final String URL = "https://api.iamport.kr";

    private OkHttpClient getClient(){
        if (CLIENT == null) {
            CLIENT= new OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        return CLIENT;
    }

    /** access token 발급. 실패한 경우 null return*/
    private String getToken(String imp_key, String imp_secret) throws IOException, ParseException{
        String url = URL +"/users/getToken";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imp_key", imp_key);
        jsonObject.put("imp_secret", imp_secret);
        RequestBody reqBody = RequestBody.create(jsonObject.toJSONString(), MediaType.parse("application/json; charset=utf-8"));
        Request req = new Request.Builder()
                .url(url)
                .post(reqBody)
                .addHeader("Content-Type", "application/json")
                .build();

        OkHttpClient client = getClient();
        Response response = null;
        try{
            response = client.newCall(req).execute();
            String response_str= response.body().string();

            JSONParser jsonParser = new JSONParser();
            JSONObject jo = (JSONObject) jsonParser.parse(response_str);

            String code = jo.get("code") + "";
            if (code.equals("0")) {
                JSONObject jo_response = (JSONObject)jo.get("response");
                return jo_response.get("access_token")+"";
            }else{
                throw new RuntimeException(jo.get("message")+"");
            }
        }finally {
            try{
                if(response != null) response.close();
            }catch (Exception e){}
        }
    }
    /** merchant id 로 결제 확인*/
    public String checkPay(String imp_key, String imp_secret, String merchant_id) throws Exception{
        String token = getToken(imp_key, imp_secret);
        if (token == null) {
            return null;
        }

        String url = URL +"/payments/find/"+merchant_id+"/";

        Request req = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("Authorization", token)
                .build();

        OkHttpClient client = getClient();
        Response response = null;
        try{
            response = client.newCall(req).execute();
            return response.body().string();
        }finally {
            try{
                if(response != null) response.close();
            }catch (Exception e){}
        }

    }
}

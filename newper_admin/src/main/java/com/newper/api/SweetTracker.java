package com.newper.api;

import com.newper.entity.DeliveryNum;
import com.newper.entity.Tracking;
import com.newper.exception.MsgException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SweetTracker {


    /**sweet tracker 도메인*/
    private final String domain="";
    /**tier*/
    private final String tier="";
    /**key*/
    private final String key="";
    /**callback*/
    private final String callback="";


    /**운송장 추적 요청*/
    public Object addInvoice(DeliveryNum deliveryNum){
        Response response = null;
        try{
            Tracking te = new Tracking();
            te.setDeliveryNum(deliveryNum);
            te.setTeType("REQUEST");

            //추적 API 생성
//            trackingRepository.saveAndFlush(te);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("callback_url", callback);
            jsonObject.put("callback_type", "json");
            jsonObject.put("tier", tier);
            jsonObject.put("key", key);
            jsonObject.put("type", "json");

            jsonObject.put("fid", te.getFid());
            jsonObject.put("num", deliveryNum.getDnNum());
            jsonObject.put("code", deliveryNum.getDnCompany());

            RequestBody reqBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toJSONString());
            te.setTeReq(jsonObject.toJSONString());

            String url = domain + "add_invoice";
            te.setTeUrl(url);
            te.setTeNum(deliveryNum.getDnNum());

            OkHttpClient client1 = new OkHttpClient.Builder().build();
            Request req = new Request.Builder()
                    .url(url)
                    .post(reqBody)
                    .addHeader("accept", "application/json")
                    .build();
            response = client1.newCall(req).execute();
            String response_str= response.body().string();
            te.setTeRes(response_str);

            JSONParser jsonParser = new JSONParser();
            JSONObject result = (JSONObject) jsonParser.parse(response_str);

            return result;
        }catch(Exception e){
            e.printStackTrace();
            throw new MsgException("오류 발생 ~~~");
        }finally {
            try{
                if(response!=null){
                    response.close();
                }
            }catch (Exception e){}
        }
    }

    /** 배송정보 조회 */
    public Object tracking(DeliveryNum deliveryNum){
        Response response = null;
        try{

            Tracking tr = null;

            List<String> fids = new ArrayList<>();
            fids.add(tr.getFid());

            JSONObject jo = new JSONObject();
            jo.put("tierCode", tier);
            jo.put("fids", fids);
            RequestBody reqBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jo.toJSONString());

            String url = domain + "/tracking";

            OkHttpClient client1 = new OkHttpClient.Builder().build();
            Request req = new Request.Builder()
                    .url(url)
                    .post(reqBody)
                    .addHeader("Content-Type", "application/json")
                    .build();
            response = client1.newCall(req).execute();
            String response_str= response.body().string();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response_str);

            return jsonObject;
        }catch (IOException ioe){
            log.warn("galaixaApi.tracking error", ioe);
        }catch (Exception e) {
            log.warn("galaixaApi.tracking error", e);
        }finally {
            try{
                if(response!=null){
                    response.close();
                }
            }catch (Exception e){}
        }
        return false;
    }



    /**스윗트래커 택배반품요청*/
    public Object returnDelivery(DeliveryNum deliveryNum){
        Response response = null;
        try{
            Tracking te = new Tracking();
            te.setDeliveryNum(deliveryNum);
            te.setTeType("REQUEST");

            //추적 API 생성
//            trackingRepository.saveAndFlush(te);

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObj = new JSONObject();
            jsonObject.put("tierCode", tier);
            jsonObject.put("cipherType", 0);

            jsonObj.put("ordCde", deliveryNum.getDnIdx());
            jsonObj.put("comCode", deliveryNum.getDnIdx());
            jsonObj.put("invoice", deliveryNum.getDnIdx());
            jsonObj.put("cusCde", deliveryNum.getDnIdx());
            jsonObj.put("comDivCde", deliveryNum.getDnIdx());
            jsonObj.put("sndNme", deliveryNum.getDnIdx());
            jsonObj.put("sndZip", deliveryNum.getDnIdx());
            jsonObj.put("sndAd1", deliveryNum.getDnIdx());
            jsonObj.put("sndAd2", deliveryNum.getDnIdx());
            jsonObj.put("sndTel", deliveryNum.getDnIdx());
            jsonObj.put("sndMod", deliveryNum.getDnIdx());
            jsonObj.put("ownNme", deliveryNum.getDnIdx());
            jsonObj.put("ownZip", deliveryNum.getDnIdx());
            jsonObj.put("ownAd1", deliveryNum.getDnIdx());
            jsonObj.put("ownAd2", deliveryNum.getDnIdx());
            jsonObj.put("ownTel", deliveryNum.getDnIdx());
            jsonObj.put("ownMod", deliveryNum.getDnIdx());
            jsonObj.put("itmLst", deliveryNum.getDnIdx());
            jsonObj.put("adMemo", deliveryNum.getDnIdx());
            jsonObj.put("wipGbn", deliveryNum.getDnIdx());
            jsonObj.put("dsoGbn", deliveryNum.getDnIdx());
            jsonObj.put("dsoWght", deliveryNum.getDnIdx());
            jsonObj.put("boxQty", deliveryNum.getDnIdx());
            jsonObj.put("priceRegGbn", deliveryNum.getDnIdx());
            jsonObj.put("priceAmt", deliveryNum.getDnIdx());


            jsonArray.add(jsonObj);


            jsonObject.put("data", jsonArray);


            RequestBody reqBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toJSONString());
            te.setTeReq(jsonObject.toJSONString());

            String url = domain + "takeback";
            te.setTeUrl(url);
            te.setTeNum(deliveryNum.getDnNum());

            OkHttpClient client1 = new OkHttpClient.Builder().build();
            Request req = new Request.Builder()
                    .url(url)
                    .post(reqBody)
                    .addHeader("accept", "application/json")
                    .build();
            response = client1.newCall(req).execute();
            String response_str= response.body().string();
            te.setTeRes(response_str);

            JSONParser jsonParser = new JSONParser();
            JSONObject result = (JSONObject) jsonParser.parse(response_str);

            return result;
        }catch(Exception e){
            e.printStackTrace();
            throw new MsgException("오류 발생 ~~~");
        }finally {
            try{
                if(response!=null){
                    response.close();
                }
            }catch (Exception e){}
        }
    }


    /**스윗트래커 택배반품조회*/
    public Object returnDeliveryCheck(DeliveryNum deliveryNum){
        Response response = null;
        try{
            Tracking te = new Tracking();
            te.setDeliveryNum(deliveryNum);
            te.setTeType("REQUEST");

            //추적 API 생성
//            trackingRepository.saveAndFlush(te);

            JSONObject jsonObject = new JSONObject();


            RequestBody reqBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toJSONString());
            te.setTeReq(jsonObject.toJSONString());

            String url = domain + "takeback/"+tier+"/"+deliveryNum.getDnIdx();
            te.setTeUrl(url);
            te.setTeNum(deliveryNum.getDnNum());

            OkHttpClient client1 = new OkHttpClient.Builder().build();
            Request req = new Request.Builder()
                    .url(url)
                    .post(reqBody)
                    .addHeader("accept", "application/json")
                    .build();
            response = client1.newCall(req).execute();
            String response_str= response.body().string();
            te.setTeRes(response_str);

            JSONParser jsonParser = new JSONParser();
            JSONObject result = (JSONObject) jsonParser.parse(response_str);

            return result;
        }catch(Exception e){
            e.printStackTrace();
            throw new MsgException("오류 발생 ~~~");
        }finally {
            try{
                if(response!=null){
                    response.close();
                }
            }catch (Exception e){}
        }
    }


    /**스윗트래커 택배반품요청 취소*/
    public Object returnDeliveryCancel(DeliveryNum deliveryNum){
        Response response = null;
        try{
            Tracking te = new Tracking();
            te.setDeliveryNum(deliveryNum);
            te.setTeType("REQUEST");

            //추적 API 생성
//            trackingRepository.saveAndFlush(te);

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObj = new JSONObject();
            jsonObject.put("tierCode", tier);
            jsonObj.put("ordCde", deliveryNum.getDnIdx());
            jsonArray.add(jsonObj);
            jsonObject.put("data", jsonArray);



            RequestBody reqBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toJSONString());
            te.setTeReq(jsonObject.toJSONString());

            String url = domain + "takeback";
            te.setTeUrl(url);
            te.setTeNum(deliveryNum.getDnNum());

            OkHttpClient client1 = new OkHttpClient.Builder().build();
            Request req = new Request.Builder()
                    .url(url)
                    .post(reqBody)
                    .addHeader("accept", "application/json")
                    .build();
            response = client1.newCall(req).execute();
            String response_str= response.body().string();
            te.setTeRes(response_str);

            JSONParser jsonParser = new JSONParser();
            JSONObject result = (JSONObject) jsonParser.parse(response_str);

            return result;
        }catch(Exception e){
            e.printStackTrace();
            throw new MsgException("오류 발생 ~~~");
        }finally {
            try{
                if(response!=null){
                    response.close();
                }
            }catch (Exception e){}
        }
    }

}


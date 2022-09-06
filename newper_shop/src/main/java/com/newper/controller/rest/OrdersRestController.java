package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.iamport.IamportApi;
import com.newper.mapper.PaymentMapper;
import com.newper.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders/")
public class OrdersRestController {

    private final OrdersService ordersService;
    private final PaymentMapper paymentMapper;

    @Autowired
    private ShopSession shopSession;


    /** iamport 결제 요청 */
    @PostMapping("iamport/pay.ajax")
    public ReturnMap iamportPay(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        shopSession.setIdx(1l);
        shopSession.setId("test");

        rm.put("id", "imp07732252");

        rm.put("req", ordersService.insertOrder(paramMap));
        return rm;
    }
    /** iamport 결제 결과 저장*/
    @PostMapping("iamport/result.ajax")
    public ReturnMap iamportResult(@RequestBody Map<String,Object> map){
        ReturnMap rm = new ReturnMap();

        System.out.println(map.entrySet());

        String ph_idx = (map.get("merchant_uid")+"").replaceAll("[^0-9]","");
        paymentMapper.updatePaymentHistoryResult(ph_idx, JSONObject.toJSONString(map));

        return rm;
    }

    @PostMapping("test.ajax")
    public void checkPay(){
        String imp_key = "7551760752994000";
        String imp_secret = "bVmJurNh2koL0ouzErz9tAbwlURA7nQjjd2NFQ5HbttbSXKwpkGTURqfLhQSFHcZUeNz5ShgmKseIcL8";

        try{
            String response_str = new IamportApi().checkPay(imp_key, imp_secret, "imp_448280090638");

            JSONParser jsonParser = new JSONParser();
            JSONObject jo = (JSONObject) jsonParser.parse(response_str);

            String code = jo.get("code") + "";
            if (code.equals("0")) {
                JSONObject jo_response = (JSONObject)jo.get("response");
//                return jo_response.get("access_token")+"";
//                amount (number): 주문(결제)금액 ,
//                status (string): 결제상태. ready:미결제, paid:결제완료, cancelled:결제취소, failed:결제실패 = ['ready', 'paid', 'cancelled', 'failed'],

            }else{
                throw new RuntimeException(jo.get("message").toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

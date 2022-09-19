package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.exception.MsgException;
import com.newper.iamport.IamportApi;
import com.newper.mapper.PaymentMapper;
import com.newper.service.OrdersService;
import com.newper.service.PaymentService;
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
    private final PaymentService paymentService;

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
    /** iamport 결제 결과 UID만 저장. JS에서 받는 결과여서 서버에서 따로 검증 단계 필요*/
    @PostMapping("iamport/result.ajax")
    public ReturnMap iamportResult(@RequestBody Map<String,Object> map){
        ReturnMap rm = new ReturnMap();

        System.out.println(map.entrySet());

        String ph_idx = (map.get("merchant_uid")+"").replaceAll("[^0-9]","");
        String ph_uid = (String)map.get("imp_uid");
        paymentMapper.updatePaymentHistoryUid(ph_idx, ph_uid);

        return rm;
    }

    /** 결제 결과 확인 후 저장*/
    @PostMapping("iamport/check.ajax")
    public void iamportCheck(long ph_idx){


        String response_str;
        try{
            String imp_key = "7551760752994000";
            String imp_secret = "bVmJurNh2koL0ouzErz9tAbwlURA7nQjjd2NFQ5HbttbSXKwpkGTURqfLhQSFHcZUeNz5ShgmKseIcL8";
            response_str = new IamportApi().checkPay(imp_key, imp_secret, "ph"+ph_idx);
        }catch (Exception e){
            throw new MsgException("결제 조회 중 에러 발생", e);
        }

        paymentService.savePaymentResult(ph_idx, response_str);

    }
}

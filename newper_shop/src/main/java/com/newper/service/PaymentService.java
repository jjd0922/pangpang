package com.newper.service;

import com.newper.constant.PhResult;
import com.newper.entity.PaymentHistory;
import com.newper.exception.MsgException;
import com.newper.iamport.IamportApi;
import com.newper.repository.PaymentHistoryRepo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentHistoryRepo paymentHistoryRepo;

    /**결제결과 저장*/
    @Transactional
    public void savePaymentResult(long ph_idx, String response_str){
        JSONParser jsonParser = new JSONParser();
        JSONObject jo = null;

        System.out.println(response_str);
        try{
            jo = (JSONObject) jsonParser.parse(response_str);
        }catch (ParseException pe){
            throw new MsgException("결제 조회 중 에러 발생", pe);
        }

        PaymentHistory paymentHistory = paymentHistoryRepo.findById(ph_idx).get();

        //결과 최초로 받은 경우에만 동작
        if (paymentHistory.getPhResult() == PhResult.WAIT) {
            paymentHistory.setPhRes(response_str);

            String message  = jo.get("message") + "";
            if(message.indexOf("존재하지 않는 결제정보입니다") != -1){
                paymentHistory.setPhResult(PhResult.FAIL);
            }else{
                String code = jo.get("code") + "";
                if (code.equals("0")) {
                    JSONObject jo_response = (JSONObject)jo.get("response");

                    String status = jo_response.get("status")+"";
                    if(status.equals("paid") || status.equals("cancelled")){
                        paymentHistory.setPhResult(PhResult.DONE);

//                        amount (number): 주문(결제)금액
                    } else if (status.equals("failed")) {
                        paymentHistory.setPhResult(PhResult.FAIL);

                    }else if(status.equals("ready")){


                    }
                }
            }


        }





    }
}

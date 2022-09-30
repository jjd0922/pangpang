package com.newper.service;

import com.newper.constant.OState;
import com.newper.constant.PayState;
import com.newper.constant.PhResult;
import com.newper.entity.Orders;
import com.newper.entity.Payment;
import com.newper.entity.PaymentHistory;
import com.newper.exception.MsgException;
import com.newper.exception.NoRollbackException;
import com.newper.iamport.IamportApi;
import com.newper.mapper.IamportMapper;
import com.newper.repository.PaymentHistoryRepo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentHistoryRepo paymentHistoryRepo;
    private final IamportMapper iamportMapper;

    /**결제결과 저장*/
    @Transactional(noRollbackFor = {NoRollbackException.class})
    public Payment savePaymentResult(long ph_idx){
        String response_str;
        try{
            response_str = new IamportApi().checkPay("ph"+ph_idx);
        }catch (Exception e){
            throw new MsgException("결제 조회 중 에러 발생", e);
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jo = null;

        try{
            jo = (JSONObject) jsonParser.parse(response_str);
        }catch (ParseException pe){
            throw new MsgException("결제 조회 중 에러 발생", pe);
        }

        PaymentHistory paymentHistory = paymentHistoryRepo.findLockByPhIdx(ph_idx);

        //proxy객체 해소용 코드
        Payment payment = paymentHistory.getPayment();
        Orders orders = payment.getOrders();

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
                    //결제 성공
                    if(status.equals("paid")){
                        paymentHistory.setPhResult(PhResult.DONE);
                        orders.setOTemp(true);

                        //amount (number): 주문(결제)금액
                        int amount = Integer.parseInt(jo_response.get("amount") + "");
                        //결제 금액 맞는지 확인
                        if (payment.getPayPrice() == amount) {
                            payment.paySuccess();
                        }else{
                        //결제 금액 불일치
                            payment.setPayState(PayState.ERROR);
                            throw new NoRollbackException("결제 금액이 일치하지 않습니다.");
                        }
                    }else if(status.equals("cancelled")){
                        paymentHistory.setPhResult(PhResult.DONE);

                        payment.setPayCancelState(PayState.SUCCESS);

                    } else if (status.equals("failed")) {
                        paymentHistory.setPhResult(PhResult.FAIL);
                        payment.setPayState(PayState.FAIL);
                    }else if(status.equals("ready")){
                        Map<String, Object> ipmMap = iamportMapper.selectIamportMethodDetail(payment.getPayIpmIdx());
                        //가상계좌
                        if( "vbank".equals((String)ipmMap.get("IPM_VALUE"))){
                            payment.setPayState(PayState.REQ);
                            orders.setOTemp(true);
                        }
                    }
                }
            }

        }

        return payment;
    }

    /** iamport 결제 취소 요청 보내기 전 확인 및, 데이터 세팅*/
    @Transactional
    public void iamportPaymentCancelReq(String oCode){
//        Payment payment = paymentr.getPayment();

//        payment.setPayCancelState(PayState.REQ);


    }
}

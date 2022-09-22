package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Orders;
import com.newper.exception.MsgException;
import com.newper.iamport.IamportApi;
import com.newper.mapper.PaymentMapper;
import com.newper.service.OrdersService;
import com.newper.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ReturnMap iamportPay(ParamMap paramMap, int ipm_idx) {
        ReturnMap rm = new ReturnMap();

        shopSession.setIdx(1l);
        shopSession.setId("test");

        rm.put("id", "imp07732252");

        Orders orders = ordersService.insertOrder(paramMap);
        JSONObject req = ordersService.insertIamportReq(orders, ipm_idx);
        rm.put("req", req);
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
    /** iamport 결제 취소*/
//    @PostMapping("iamport/cancel.ajax")
    @GetMapping("iamport/cancel.ajax")
    public ReturnMap iamportCancel(String oCode){
        ReturnMap rm = new ReturnMap();

        //결제한 사람 맞는지 확인하는 코드
        //회원인 경우

        //비회원인 경우


        paymentService.iamportPaymentCancelReq(oCode);
        String response_str;
        try{
            long phIdx = 27;
            response_str = new IamportApi().cancelPay("ph"+phIdx);
            System.out.println(response_str);

            //{"code":0,"message":null,"response":{"amount":100,"apply_num":"30046339","bank_code":null,"bank_name":null,"buyer_addr":null,"buyer_email":null,"buyer_name":"\uc8fc\ubb38\uc790\uba85","buyer_postcode":null,"buyer_tel":null,"cancel_amount":100,"cancel_history":[{"pg_tid":"22895235578509","amount":100,"cancelled_at":1663837543,"reason":"\ucde8\uc18c\uc694\uccadapi","receipt_url":"https:\/\/admin8.kcp.co.kr\/assist\/bill.BillActionNew.do?cmd=card_bill&tno=22895235578509&order_no=imp_803037736674&trade_mony=100"}],"cancel_reason":"\ucde8\uc18c\uc694\uccadapi","cancel_receipt_urls":["https:\/\/admin8.kcp.co.kr\/assist\/bill.BillActionNew.do?cmd=card_bill&tno=22895235578509&order_no=imp_803037736674&trade_mony=100"],"cancelled_at":1663837543,"card_code":"381","card_name":"KB\uad6d\ubbfc\uce74\ub4dc","card_number":"5570420000007221","card_quota":0,"card_type":0,"cash_receipt_issued":false,"channel":"pc","currency":"KRW","custom_data":null,"customer_uid":null,"customer_uid_usage":null,"emb_pg_provider":null,"escrow":false,"fail_reason":null,"failed_at":0,"imp_uid":"imp_803037736674","merchant_uid":"ph27","name":"test \uc678 1\uac74","paid_at":1663830095,"pay_method":"card","pg_id":"A7NC2","pg_provider":"kcp","pg_tid":"22895235578509","receipt_url":"https:\/\/admin8.kcp.co.kr\/assist\/bill.BillActionNew.do?cmd=card_bill&tno=22895235578509&order_no=imp_803037736674&trade_mony=100","started_at":1663830037,"status":"cancelled","user_agent":"Mozilla\/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit\/537.36 (KHTML, like Gecko) Chrome\/105.0.0.0 Safari\/537.36","vbank_code":null,"vbank_date":0,"vbank_holder":null,"vbank_issued_at":0,"vbank_name":null,"vbank_num":null}}
            rm.put("res", response_str);
        }catch (Exception e){
            throw new MsgException("결제 취소 중 에러 발생", e);
        }

        return rm;
    }
}

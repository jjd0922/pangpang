package com.newper.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newper.component.ShopSession;
import com.newper.constant.OLocation;
import com.newper.constant.PayState;
import com.newper.constant.etc.IamPortPayMethod;
import com.newper.constant.etc.IamPortPg;
import com.newper.dto.IamportReq;
import com.newper.dto.ParamMap;
import com.newper.entity.Orders;
import com.newper.entity.Payment;
import com.newper.entity.PaymentHistory;
import com.newper.exception.MsgException;
import com.newper.repository.CustomerRepo;
import com.newper.repository.OrdersRepo;
import com.newper.repository.PaymentHistoryRepo;
import com.newper.repository.PaymentRepo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final CustomerRepo customerRepo;
    private final OrdersRepo ordersRepo;
    private final PaymentRepo paymentRepo;
    private final PaymentHistoryRepo paymentHistoryRepo;
    @Autowired
    private ShopSession shopSession;

    /** insert order*/
    @Transactional
    public JSONObject insertOrder(ParamMap paramMap){
        LocalDateTime now = LocalDateTime.now();

        Orders orders = Orders.builder()
                .customer(customerRepo.getReferenceById(shopSession.getIdx()))
                .oDate(now.toLocalDate())
                .oTime(now.toLocalTime())
                .oLocation(OLocation.SHOP)
                .oMemo("")
                .oName("")
                .oPhone("01085434628")
                .build();

        Payment payment = Payment.builder()
                .payMethod("test")
                .payPrice(1000)
                .payProductPrice(1000)
                .build();


        orders.setPayment(payment);

        paymentRepo.save(payment);


        PaymentHistory paymentHistory = payment.createPayReq();
        paymentHistoryRepo.save(paymentHistory);

        //merchant_uid ph_idx사용
        IamportReq iamportReq = new IamportReq(IamPortPayMethod.CARD, "ph"+paymentHistory.getPhIdx(), payment.getPayPrice(), orders.getOPhone());
        iamportReq.setPg(IamPortPg.BLUEWALNUT.getPg());
        iamportReq.setName("test주문");
        iamportReq.setBuyer_name("구매자");

        try{
            ObjectMapper om = new ObjectMapper();
            om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            String phReq = om.writeValueAsString(iamportReq);
            paymentHistory.setPhReq(phReq);

            return (JSONObject) new JSONParser().parse(phReq);
        }catch (ParseException jpe){
            throw new MsgException("parsing error");
        }catch (JsonProcessingException jpe){
            throw new MsgException("parsing error");
        }

    }
}

package com.newper.service;

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
    public IamportReq insertOrder(ParamMap paramMap){
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

        IamportReq iamportReq = new IamportReq(IamPortPayMethod.CARD, paymentHistory.getMerchantId(), payment.getPayPrice(), orders.getOPhone());
        iamportReq.setPg(IamPortPg.BLUEWALNUT.getPg());
        iamportReq.setName("test주문");

        try{
            String phReq = new ObjectMapper().writeValueAsString(iamportReq);
            System.out.println(phReq);
            paymentHistory.setPhReq(phReq);
        }catch (JsonProcessingException jpe){
            throw new MsgException("parsing error");
        }

        return iamportReq;
    }
}

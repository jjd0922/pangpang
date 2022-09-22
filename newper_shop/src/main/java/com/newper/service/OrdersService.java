package com.newper.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newper.component.ShopSession;
import com.newper.constant.OLocation;
import com.newper.constant.PayMethod;
import com.newper.dto.IamportReq;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.IamportMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final CustomerRepo customerRepo;
    private final OrdersRepo ordersRepo;
    private final PaymentRepo paymentRepo;
    private final PaymentHistoryRepo paymentHistoryRepo;
    private final IamportMapper iamportMapper;
    private final ShopProductOptionRepo shopProductOptionRepo;

    @Autowired
    private ShopSession shopSession;

    /** insert orders, payment*/
    @Transactional
    public Orders insertOrder(ParamMap paramMap){
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
                .payMethod(PayMethod.CARD)
                .payPrice(1000)
                .payProductPrice(1000)
                .build();

        for(long i=1;i<3;i++){

            OrderGs orderGs = OrderGs.builder()
                    .orders(orders)
                    .build();

            ShopProductOption shopProductOption = shopProductOptionRepo.findById(i).get();
            orderGs.setShopProductOption(shopProductOption);

        }



        orders.setPayment(payment);
        paymentRepo.save(payment);

        return orders;
    }
    /** iamport 결제요청 데이터 세팅*/
    @Transactional
    public JSONObject insertIamportReq(Orders orders, int ipm_idx){
        Payment payment = paymentRepo.findLockByPayIdx(orders.getPayment().getPayIdx());
        PaymentHistory paymentHistory = payment.createPayReq();
        paymentHistoryRepo.save(paymentHistory);

        Map<String, Object> ipmMap = iamportMapper.selectIamportMethodDetail(ipm_idx);
        if(ipmMap == null){
            throw new MsgException("지원하지 않는 결제수단입니다");
        }

        IamportReq iamportReq = new IamportReq(paymentHistory.getPhIdx());
        iamportReq.setKcpInfo((String)ipmMap.get("mid"),
                (String)ipmMap.get("IPM_VALUE"),
                orders.getOrderPaymentTitle(),
                payment.getPayPrice(),
                "",
                orders.getOName(),
                "",
                "",
                "");

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

package com.newper.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newper.component.ShopSession;
import com.newper.constant.OLocation;
import com.newper.constant.PhType;
import com.newper.dto.IamportReq;
import com.newper.dto.OrdersSpoDTO;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.exception.NoSessionException;
import com.newper.iamport.IamportApi;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final CustomerRepo customerRepo;
    private final OrdersRepo ordersRepo;
    private final ShopRepo shopRepo;
    private final PaymentRepo paymentRepo;
    private final PaymentHistoryRepo paymentHistoryRepo;
    private final IamportMapper iamportMapper;
    private final ShopProductOptionRepo shopProductOptionRepo;
    private final ShopProductService shopProductService;

    @Autowired
    private ShopSession shopSession;

    /** insert orders, payment*/
    @Transactional
    public Orders insertOrder(ParamMap paramMap){
        LocalDateTime now = LocalDateTime.now();

        //회원 주문인데 세션 만료된 경우 alert 띄우기 (비회원 주문 가능). 회원 주문 적립금 때문에 구분해서 체크함
        String login = paramMap.getString("login");
        Customer customer = null;
        if (login.equals("true")) {
            if(shopSession.getIdx() == null){
                throw new NoSessionException();
            }
            customer = customerRepo.getReferenceById(shopSession.getIdx());
        }

        Orders orders = paramMap.mapParam(Orders.class);
        orders.setCustomer(customer);
        orders.setODate(now.toLocalDate());
        orders.setOTime(now.toLocalTime());
        orders.setOLocation(OLocation.SHOP);
        orders.setShop(shopRepo.getReferenceById(shopSession.getShopIdx()));

        List<OrderGs> orderGsList = new ArrayList<>();
        Map<ShopProduct, List<OrdersSpoDTO>> spoList = shopProductService.selectOrdersInfo(paramMap);

        int usedPoint = 0;
        int usedMileage = 0;
        int delivery = 0;
        int product_price = 0;
        for (ShopProduct shopProduct : spoList.keySet()) {
            for (OrdersSpoDTO dto : spoList.get(shopProduct)) {
                for (int i = 0 ;i < dto.getCnt(); i++) {
                    for (ShopProductOption spo : dto.getSpoList()) {
                        OrderGs orderGs = OrderGs.builder()
                                .orders(orders)
                                .shopProductOption(spo)
                                .ogPrice(spo.getSpoPrice())
                                .ogSpo(dto.getVal()+"|"+i)
                                .build();
                        orderGsList.add(orderGs);

                        product_price += spo.getSpoPrice();
                    }
                }
            }
        }
        orders.setOrderGs(orderGsList);

        int ipm_idx = paramMap.getInt("ipm_idx");
        Payment payment = Payment.builder()
                .payPrice(product_price - usedPoint - usedMileage + delivery)
                .payProductPrice(product_price - usedPoint - usedMileage)
                .payDelivery(delivery)
//                .payMileage()
                .payIpmIdx(ipm_idx)
                .build();

        orders.setPayment(payment);
        payment.calculatePrice();

        paymentRepo.save(payment);

        return orders;
    }
    /** iamport 결제요청 데이터 세팅*/
    @Transactional
    public JSONObject insertIamportReq(Orders orders){
        Payment payment = paymentRepo.findLockByPayIdx(orders.getPayment().getPayIdx());
        PaymentHistory paymentHistory = payment.createPayReq();
        paymentHistoryRepo.save(paymentHistory);

        Map<String, Object> ipmMap = iamportMapper.selectIamportMethodDetail(payment.getPayIpmIdx());
        if(ipmMap == null){
            throw new MsgException("지원하지 않는 결제수단입니다");
        }
        payment.putPayJson("pay_method", ipmMap.get("IPM_NAME"));

        IamportReq iamportReq = new IamportReq(paymentHistory.getPhIdx(),
                (String)ipmMap.get("PG"),
                payment.getPayPrice(),
                orders.getOrderPaymentTitle());

        String ippValue = (String) ipmMap.get("IPP_VALUE");
        switch (ippValue){
            case "kcp":{
                iamportReq.setKcp(
                        (String)ipmMap.get("IPM_VALUE"),
                        "",
                        orders.getOName(),
                        orders.getOPhone(),
                        "",
                        "");
                break;
            }
            case "naverpay":{
                iamportReq.setNaverpay(orders.getOName());
                break;
            }
            case "kakaopay" : {
                iamportReq.setKakaopay();
                break;
            }
            case "payco":{
                iamportReq.setPayco(orders.getOPhone());
                break;
            }
            case "chai":{
                iamportReq.setChai(orders.getOName());
                break;
            }
        }

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
    /** 주문 상세 페이지 데이터 조회. 결제 결과 확인*/
    @Transactional
    public Orders selectOrdersDetail(String oCode){
        Orders orders = ordersRepo.findInfoByoCode(oCode);
        if(!orders.isOTemp()){
            throw new MsgException("주문 내역이 없습니다");
        }
        if(shopSession.getIdx().longValue() != orders.getCustomer().getCuIdx().longValue()){
            throw new MsgException("접근 권한이 없습니다");
        }

        Payment payment = orders.getPayment();

        return orders;
    }
}

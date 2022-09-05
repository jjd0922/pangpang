package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.constant.etc.IamPortPayMethod;
import com.newper.dto.IamportReq;
import com.newper.dto.ParamMap;
import com.newper.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
@RequestMapping("/orders/")
public class OrdersRestController {

    private final OrdersService ordersService;

    @Autowired
    private ShopSession shopSession;


    /** iamport 결제 요청 */
    @PostMapping("iamport.ajax")
    public IamportReq iamport(ParamMap paramMap) {

        shopSession.setIdx(1l);
        shopSession.setId("test");

        ordersService.insertOrder(paramMap);

        IamportReq iamportReq = new IamportReq(IamPortPayMethod.CARD, "test123", 100,"01085434628");



        return iamportReq;
    }
}

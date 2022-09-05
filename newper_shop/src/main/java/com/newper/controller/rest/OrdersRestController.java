package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.constant.etc.IamPortPayMethod;
import com.newper.dto.IamportReq;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders/")
public class OrdersRestController {

    private final OrdersService ordersService;

    @Autowired
    private ShopSession shopSession;


    /** iamport 결제 요청 */
    @PostMapping("iamport.ajax")
    public ReturnMap iamport(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        shopSession.setIdx(1l);
        shopSession.setId("test");

        rm.put("id", "imptest01m");
        IamportReq iamportReq = ordersService.insertOrder(paramMap);
        rm.put("req", iamportReq);
        return rm;
    }
}

package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.constant.etc.IamPortPayMethod;
import com.newper.dto.IamportReq;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/")
public class OrderRestController {

    private final OrderService orderService;

    @Autowired
    private ShopSession shopSession;


    /** iamport 결제 요청 */
    @PostMapping("iamport.ajax")
    public IamportReq iamport(ParamMap paramMap) {

        shopSession.setIdx(1l);
        shopSession.setId("test");

        orderService.insertOrder(paramMap);

        IamportReq iamportReq = new IamportReq(IamPortPayMethod.CARD, "test123", 100,"01085434628");



        return iamportReq;
    }
}

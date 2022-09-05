package com.newper.controller.rest;

import com.newper.constant.etc.IamPortPayMethod;
import com.newper.dto.IamportReq;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/")
public class OrderRestController {

    /** iamport 결제 요청 */
    @PostMapping("iamport.ajax")
    public IamportReq iamport(ParamMap paramMap) {

        IamportReq iamportReq = new IamportReq(IamPortPayMethod.CARD, "test123", 100,"01085434628");



        return iamportReq;
    }
}

package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon/")
@RequiredArgsConstructor
public class CouponRestController {

    private final CouponService couponService;

    /**쿠폰그룹 신규등록*/
    @PostMapping("save.ajax")
    public ReturnMap saveCoupon(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        couponService.saveCoupon(paramMap);
        rm.setMessage("등록완료");
        return rm;
    }
    
}

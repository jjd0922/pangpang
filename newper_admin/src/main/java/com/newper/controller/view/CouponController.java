package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coupon/")
public class CouponController {

    /**쿠폰생성관리 페이지*/
    @GetMapping("")
    public ModelAndView coupon() {
        ModelAndView mav = new ModelAndView("/coupon/coupon");

        return mav;
    }

    @GetMapping("/detail")
    public ModelAndView newCouponPop() {
        ModelAndView mav = new ModelAndView("/coupon/detail");

        return mav;
    }


}

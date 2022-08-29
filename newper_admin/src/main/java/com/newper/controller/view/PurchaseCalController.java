package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequiredArgsConstructor
@RequestMapping("/purchaseCal/")
public class PurchaseCalController {


    /**벤더 정산 설정 페이지*/
    @GetMapping(value = "vendorSetting")
    public ModelAndView vendorSetting(){
        ModelAndView mav = new ModelAndView("purchaseCal/vendorSetting");

        return mav;
    }

    /**벤더 정산 팝업*/
    @GetMapping(value = "vendorPop")
    public ModelAndView vendorPop(){
        ModelAndView mav = new ModelAndView("purchaseCal/vendorPop");

        return mav;
    }



}

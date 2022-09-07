package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/salesManagement/")
@RequiredArgsConstructor
@RestController
public class SalesManagementController {

    /**주문관리 배송관리 페이지*/
    @GetMapping("deliveryManagement")
        public ModelAndView deliveryManagement(){
        ModelAndView mav = new ModelAndView("salesManagement/deliveryManagement");

        return mav;
    }

    /**주문관리 배송관리 주문코드 팝업 페이지*/
    @GetMapping("deliveryManagementPop")
    public ModelAndView deliveryManagementPop(){
        ModelAndView mav = new ModelAndView("salesManagement/deliveryManagementPop");

        return mav;
    }

    /**주문관리 설치관리 페이지*/
    @GetMapping("installationManagement")
    public ModelAndView installationManagement(){
        ModelAndView mav = new ModelAndView("salesManagement/installationManagement");

        return mav;
    }


}

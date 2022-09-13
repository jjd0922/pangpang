package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/deliveryManagement/")
@RequiredArgsConstructor
@RestController
public class deliveryManagementController {

    /**주문관리 배송관리 페이지*/
    @GetMapping("delivery")
        public ModelAndView delivery(){
        ModelAndView mav = new ModelAndView("deliveryManagement/delivery");

        return mav;
    }

    /**주문관리 배송관리 주문코드 팝업 페이지*/
    @GetMapping("invoiceUploadPop")
    public ModelAndView invoiceUploadPop(){
        ModelAndView mav = new ModelAndView("deliveryManagement/invoiceUploadPop");
        return mav;
    }

    /**주문관리 설치관리 페이지*/
    @GetMapping("installation")
    public ModelAndView installation(){
        ModelAndView mav = new ModelAndView("deliveryManagement/installation");

        return mav;
    }

    /**주문관리 배송상품반품관리 페이지*/
    @GetMapping("deliveryReturn")
    public ModelAndView deliveryReturn(){
        ModelAndView mav = new ModelAndView("deliveryManagement/deliveryReturn");

        return mav;
    }

    /**주문관리 설치상품반품관리 페이지*/
    @GetMapping("installationReturn")
    public ModelAndView installationReturn(){
        ModelAndView mav = new ModelAndView("deliveryManagement/installationReturn");

        return mav;
    }

}

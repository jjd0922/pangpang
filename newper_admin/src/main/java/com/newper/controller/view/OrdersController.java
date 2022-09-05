package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping(value = "/orders/")
@Controller
@RequiredArgsConstructor
public class OrdersController {



    /**통합주문관리*/
    @GetMapping("")
    public ModelAndView order(){
        ModelAndView mav = new ModelAndView("orders/order");

        return mav;
    }

    @GetMapping("detail")
    public ModelAndView detail(){
        ModelAndView mav = new ModelAndView("orders/detail");
        return mav;
    }




}

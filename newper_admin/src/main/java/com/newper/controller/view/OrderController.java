package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping(value = "/order/")
@Controller
@RequiredArgsConstructor
public class OrderController {



    /**통합주문관리*/
    @GetMapping("")
    public ModelAndView category(){
        ModelAndView mav = new ModelAndView("order/order");

        return mav;
    }



}

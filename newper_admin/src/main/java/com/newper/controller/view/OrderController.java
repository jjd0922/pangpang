package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping(value = "/order/")
@Controller
@RequiredArgsConstructor
public class OrderController {



    /**통합주문관리*/
    @GetMapping("")
    public ModelAndView order(){
        ModelAndView mav = new ModelAndView("order/order");

        return mav;
    }

    @GetMapping("detail")
    public ModelAndView detail(){
        ModelAndView mav = new ModelAndView("order/detail");
        return mav;
    }




}

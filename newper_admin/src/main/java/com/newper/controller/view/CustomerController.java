package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/customer/")
public class CustomerController {

    @GetMapping(value = "")
    public ModelAndView customer() {
        ModelAndView mav = new ModelAndView("/customer/customer");

        return mav;
    }
    /**발송내역 페이지*/
    @GetMapping(value = "sendingHistory")
    public ModelAndView sendingHistory(){
        ModelAndView mav = new ModelAndView("customer/sendingHistory");

        return mav;
    }

}

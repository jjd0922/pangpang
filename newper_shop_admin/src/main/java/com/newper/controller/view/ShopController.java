package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/shop/")
@Controller
@RequiredArgsConstructor
public class ShopController {




    /**분양몰 관리*/
    @GetMapping("")
    public ModelAndView shop(){
        ModelAndView mav = new ModelAndView("shop/shop");

        return mav;
    }
    /**분양몰 신규, 상세 팝업*/
    @GetMapping(value = {"{shopIdx}", "new"})
    public ModelAndView shopIdx(@PathVariable(required = false) Integer shopIdx){
        ModelAndView mav = new ModelAndView("shop/shopIdx");

        //상세
        if(shopIdx != null){

        }

        return mav;
    }



}

package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/** 입고관리 controller*/
@RequestMapping(value = "/in/")
@RequiredArgsConstructor
@Controller
public class InController {

    /** 입고관리*/
    @GetMapping("")
    public ModelAndView in(){
        ModelAndView mav = new ModelAndView("in/in");

        return mav;
    }

    /** 입고검수*/
    @GetMapping("inCheck")
    public ModelAndView inCheck(){
        ModelAndView mav = new ModelAndView("in/inCheck");

        return mav;
    }

}

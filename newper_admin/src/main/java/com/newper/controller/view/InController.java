package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/** 입고등록 controller*/
@RequestMapping(value = "/in/")
@RequiredArgsConstructor
@Controller
public class InController {

    /** 입고등록*/
    @GetMapping("")
    public ModelAndView in(){
        ModelAndView mav = new ModelAndView("in/in");

        return mav;
    }
    @GetMapping("po/{poIdx}")
    public ModelAndView inIdx(@PathVariable int poIdx){
        ModelAndView mav = new ModelAndView("in/po_poIdx");

        return mav;
    }

    /** 입고검수 페이지*/
    @GetMapping("incheck")
    public ModelAndView incheck(){
        ModelAndView mav = new ModelAndView("in/incheck");

        return mav;
    }

    /** 입고검수 입고검수코드 팝업*/
    @GetMapping("incheckManagePop")
    public ModelAndView incheckManagePop(){
        ModelAndView mav = new ModelAndView("in/incheckManagePop");

        return mav;
    }
}

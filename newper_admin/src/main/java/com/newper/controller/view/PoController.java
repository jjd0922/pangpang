package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping(value = "/po/")
@Controller
@RequiredArgsConstructor
public class PoController {


    /** 견적서 관리 페이지 **/
    @GetMapping(value = "estimate")
    public ModelAndView estimate(){
        ModelAndView mav = new ModelAndView("po/estimate");
        return mav;
    }

    /** 견적서 관리 페이지 **/
    @GetMapping(value = "estimateNew")
    public ModelAndView estimateNew(){
        ModelAndView mav = new ModelAndView("po/estimateNew");
        return mav;
    }

    /** 견적서 관리 페이지 **/
    @GetMapping(value = "estimateDetail/{peIdx}")
    public ModelAndView estimateDetail(@PathVariable long peIdx){
        ModelAndView mav = new ModelAndView("po/estimateDetail");
        return mav;
    }
}

package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/process/")
@RequiredArgsConstructor
public class ProcessController {

    @GetMapping(value = "")
    public ModelAndView process() {
        ModelAndView mav = new ModelAndView("process/board");

        return mav;
    }

    @GetMapping(value = "incheckPop")
    public ModelAndView incheckPopup() {
        ModelAndView mav = new ModelAndView("process/incheckPop");

        return mav;


    }
    @GetMapping(value = "needPop")
    public ModelAndView needPopup() {
        ModelAndView mav = new ModelAndView("process/needPop");

        return mav;


    }
    @GetMapping(value = "fixPop")
    public ModelAndView fixPopup() {
        ModelAndView mav = new ModelAndView("process/fixPop");

        return mav;


    }
    @GetMapping(value = "paintPop")
    public ModelAndView paintPopup() {
        ModelAndView mav = new ModelAndView("process/paintPop");

        return mav;


    }
    @GetMapping(value = "recheckPop")
    public ModelAndView recheckPopup() {
        ModelAndView mav = new ModelAndView("process/recheckPop");

        return mav;


    }
}

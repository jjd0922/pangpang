package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor

public class MainController {

    @GetMapping(value = { "", "index"})
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("index");

        return mav;
    }

    @GetMapping(value = "mainMenu/index")
    public ModelAndView mainIndex(){
        ModelAndView mav = new ModelAndView("mainMenu/index");

        return mav;
    }

    @GetMapping(value = "test")
    public ModelAndView test(){
        ModelAndView mav = new ModelAndView("mainMenu/test");

        return mav;
    }
    @GetMapping(value = "best")
    public ModelAndView best(){
        ModelAndView mav = new ModelAndView("mainMenu/best");

        return mav;
    }
}

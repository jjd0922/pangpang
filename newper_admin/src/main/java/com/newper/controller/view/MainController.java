package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping(value = {"","index"})
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("index");


        return mav;
    }

}

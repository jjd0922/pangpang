package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/company/")
@Controller
@RequiredArgsConstructor
public class CompanyController {

    @GetMapping(value = "")
    public ModelAndView company(){
        ModelAndView mav = new ModelAndView("company/company");

        return mav;
    }
}

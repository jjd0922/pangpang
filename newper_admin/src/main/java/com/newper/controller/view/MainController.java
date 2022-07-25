package com.newper.controller.view;

import com.newper.component.SessionInfo;
import com.newper.constant.ComState;
import com.newper.entity.Company;
import com.newper.repository.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CompanyRepo companyRepo;

    @Autowired
    private SessionInfo sessionInfo;

    @GetMapping(value = {"","index"})
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("main/index");


        System.out.println(sessionInfo.getId());

        return mav;
    }
    @GetMapping(value = "home")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView("main/home");

        return mav;
    }



}

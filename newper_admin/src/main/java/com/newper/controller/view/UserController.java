package com.newper.controller.view;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user/")
@RequiredArgsConstructor
public class UserController {
    @GetMapping(value = "")
    public ModelAndView user(){
        ModelAndView mav = new ModelAndView("user/user");

        return mav;
    }

    @GetMapping(value = "userpopup")
    public ModelAndView userpopup(){
        ModelAndView mav = new ModelAndView("user/userpopup");

        return mav;
    }
}

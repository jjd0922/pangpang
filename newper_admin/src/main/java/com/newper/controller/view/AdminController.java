package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminController {
    /**뮨자 템플릿 페이지*/
    @GetMapping(value = "msgTemplate")
    public ModelAndView msgTemplate(){
        ModelAndView mav = new ModelAndView("admin/msgTemplate");

        return mav;
    }
    /**카카오톡 템플릿 페이지*/
    @GetMapping(value = "kakaoTemplate")
    public ModelAndView kakaoTemplate(){
        ModelAndView mav = new ModelAndView("admin/kakaoTemplate");

        return mav;
    }



    /**문자 템플릿 팝업페이지*/
    @GetMapping(value = "msgPop")
    public ModelAndView msgPop(){
        ModelAndView mav = new ModelAndView("admin/msgPop");

        return mav;
    }

}

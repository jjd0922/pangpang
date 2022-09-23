package com.newper.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customer/")
public class CustomerController {

    /** auth - 회원가입 안내 */
    @GetMapping(value = "joinWelcome")
    public ModelAndView joinWelcome(){
        ModelAndView mav = new ModelAndView("customer/joinWelcome");
        return mav;
    }

    /** auth - 회원가입 정보입력 */
    @GetMapping(value = "join")
    public ModelAndView join(){
        ModelAndView mav = new ModelAndView("customer/join");
        return mav;
    }

    /** auth - 회원가입 완료 */
    @GetMapping(value = "joinComplete")
    public ModelAndView joinComplete(){
        ModelAndView mav = new ModelAndView("customer/joinComplete");
        return mav;
    }
}

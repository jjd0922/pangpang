package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.entity.Auth;
import com.newper.repository.AuthRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/auth/")
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthRepo authRepo;

    @GetMapping("")
    public ModelAndView auth(){
        ModelAndView mav = new ModelAndView("auth/auth");

        return mav;
    }
    @GetMapping("menusTbody")
    public ModelAndView menusTbody(){
        ModelAndView mav = new ModelAndView("auth/menusTbody");

        mav.addObject("list", authRepo.findAll());
        return mav;
    }

}

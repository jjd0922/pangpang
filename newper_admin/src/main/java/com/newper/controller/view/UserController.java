package com.newper.controller.view;


import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Company;
import com.newper.entity.User;
import com.newper.repository.CompanyRepo;
import com.newper.repository.UserRepo;
import com.newper.service.CompanyService;
import com.newper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(value = "/user/")
@RequiredArgsConstructor
public class UserController {


    private final UserRepo userRepo;
    private final UserService userService;

    private final CompanyService companyService;

    private final CompanyRepo companyRepo;


    @GetMapping(value = "")
    public ModelAndView user() {
        ModelAndView mav = new ModelAndView("user/user");

        return mav;
    }

    @GetMapping(value = "userPopup")
    public ModelAndView userPopup() {
        ModelAndView mav = new ModelAndView("user/userPopup");

        return mav;


    }
}



package com.newper.controller.view;

import com.newper.constant.AuthMask;
import com.newper.dto.ParamMap;
import com.newper.entity.Auth;
import com.newper.repository.AuthRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping(value = "/auth/")
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthRepo authRepo;

    @GetMapping("")
    public ModelAndView auth(){
        ModelAndView mav = new ModelAndView("auth/auth");

        //masking 정보 map
        Map<String, List<AuthMask>> amMap = Arrays.stream(AuthMask.values()).collect(Collectors.groupingBy(AuthMask::getOptionGroup));
        mav.addObject("authMap", amMap);

        return mav;
    }

}

package com.newper.controller.view;


import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Company;
import com.newper.entity.User;
import com.newper.mapper.UserMapper;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/user/")
@RequiredArgsConstructor
public class UserController {


    private final UserRepo userRepo;

    private UserMapper userMapper;

    private UserService userService;

    /**
     * 사용자 관리 페이지
     */
    @GetMapping(value = "")
    public ModelAndView user() {
        ModelAndView mav = new ModelAndView("user/user");

        return mav;
    }

    /**
     * 사용자 신규등록 팝업
     */
    @GetMapping(value = "userPopup")
    public ModelAndView userPopup() {
        ModelAndView mav = new ModelAndView("user/userPopup");

        return mav;


    }

    /**
     * 사용자 상세조회 페이지
     */
    @GetMapping("userPopup/{uIdx}")
    public ModelAndView userDetail(@PathVariable Integer uIdx) {
        ModelAndView mav = new ModelAndView("user/userPopup");
        User user = userRepo.findUserByuIdx(uIdx);

        mav.addObject("user", user);
        return mav;
    }


    /**
     * 사용자등록 수정 처리
     */
    @PostMapping("userPopup/{uIdx}")
    public ModelAndView updateUser(@PathVariable Integer uIdx, ParamMap paramMap) {
        ModelAndView mav = new ModelAndView("main/alertMove");
        System.out.println(paramMap.getMap());
        paramMap.put("U_IDX", uIdx);
        userService.updateUser(paramMap);
        mav.addObject("msg", "수정 완료");
        mav.addObject("loc", "user/userPopup" + uIdx);
        return mav;
    }
}



package com.newper.controller.view;

import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/design/")
@Controller
@RequiredArgsConstructor
public class DesignController {

    private final ShopRepo shopRepo;

    /** 공통 디자인 영역*/
    @GetMapping("")
    public ModelAndView commonDesign(){
        ModelAndView mav = new ModelAndView("design/commonDesign");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }

    /** 배너 관리*/
    @GetMapping("banner")
    public ModelAndView banner(){
        ModelAndView mav = new ModelAndView("design/banner");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }
}

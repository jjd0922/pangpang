package com.newper.controller.view;

import com.newper.mapper.ShopMapper;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RequestMapping(value = "/design/")
@Controller
@RequiredArgsConstructor
public class DesignController {

    private final ShopRepo shopRepo;
    private final ShopMapper shopMapper;

    /** 공통 디자인 영역*/
    @GetMapping("")
    public ModelAndView commonDesign(){
        ModelAndView mav = new ModelAndView("design/commonDesign");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }

    /** 분양몰 디자인*/
    @GetMapping(value = "pop/design/{shopIdx}")
    public ModelAndView shopDesign(@PathVariable Integer shopIdx){
        ModelAndView mav = new ModelAndView("design/design");

        Map<String,Object> shopDesign = shopMapper.selectShopDesignJson(shopIdx);
        mav.addObject("shopDesign", shopDesign);
        return mav;
    }
    /** 분양몰 헤더*/
    @GetMapping(value = "pop/header/{shopIdx}")
    public ModelAndView shopHeader(@PathVariable Integer shopIdx){
        ModelAndView mav = new ModelAndView("design/header");

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

package com.newper.controller.view;

import com.newper.entity.MainSection;
import com.newper.exception.MsgException;
import com.newper.mapper.ShopMapper;
import com.newper.repository.MainSectionRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RequestMapping(value = "/design/")
@Controller
@RequiredArgsConstructor
public class DesignController {

    private final ShopRepo shopRepo;
    private final MainSectionRepo mainSectionRepo;
    private final ShopMapper shopMapper;

    /** 공통 디자인 영역*/
    @GetMapping("")
    public ModelAndView commonDesign(){
        ModelAndView mav = new ModelAndView("design/commonDesign");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }
    /** 메인메뉴 관리*/
    @GetMapping("menu")
    public ModelAndView mainMenu(){
        ModelAndView mav = new ModelAndView("design/mainMenu");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }
    /** 메인화면 관리*/
    @GetMapping("mainsection")
    public ModelAndView mainSection(){
        ModelAndView mav = new ModelAndView("design/mainsection");

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

    /** 분양몰 디자인*/
    @GetMapping(value = "pop/design/{shopIdx}")
    public ModelAndView shopDesign(@PathVariable Integer shopIdx){
        ModelAndView mav = new ModelAndView("design/pop_design");

        Map<String,Object> shopDesign = shopMapper.selectShopDesignJson(shopIdx);
        mav.addObject("shopDesign", shopDesign);
        return mav;
    }
    /** 분양몰 헤더*/
    @GetMapping(value = "pop/header/{shopIdx}")
    public ModelAndView shopHeader(@PathVariable Integer shopIdx){
        ModelAndView mav = new ModelAndView("design/pop_header");

        return mav;
    }

    /** 메인섹션 신규, 상세*/
    @GetMapping(value = {"mainsection/{msIdx}","mainsection/new"})
    public ModelAndView mainSection(@PathVariable(required = false) Long msIdx){
        ModelAndView mav = new ModelAndView("design/mainsection_msIdx");

        if(msIdx != null){
            MainSection mainsection = mainSectionRepo.findMainSectionBymsIdx(msIdx);
            mav.addObject("mainsection", mainsection);
        }
        return mav;
    }

    @PostMapping("fragment/mainsection/{msType}.load")
    public ModelAndView mainSectionBanner(@PathVariable String msType){
        ModelAndView mav = new ModelAndView("design/fragment/mainsection_fragment :: " + msType);

        return mav;
    }
    /** 메인섹션 배너 신규, 상세*/
    @GetMapping(value = {"mainsection/banner/{msbnIdx}","mainsection/banner/new"})
    public ModelAndView mainSectionBanner(@PathVariable(required = false) Long msbnIdx){
        ModelAndView mav = new ModelAndView("design/mainsection_msbnIdx");

        return mav;
    }
    /** 메인섹션 배너 신규, 상세*/
    @GetMapping(value = {"mainsection/shopProduct/{msspIdx}","mainsection/shopProduct/new"})
    public ModelAndView mainSectionShopProduct(@PathVariable(required = false) Long msspIdx){
        ModelAndView mav = new ModelAndView("design/mainsection_msspIdx");

        return mav;
    }
}

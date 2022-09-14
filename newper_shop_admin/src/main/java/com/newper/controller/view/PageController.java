package com.newper.controller.view;

import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/page/")
@Controller
@RequiredArgsConstructor
public class PageController {

    private final ShopRepo shopRepo;

    /** 메인메뉴 관리*/
    @GetMapping("menu")
    public ModelAndView mainMenu(){
        ModelAndView mav = new ModelAndView("page/mainMenu");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }

    /** 메인페이지 관리*/
    @GetMapping("mainsection")
    public ModelAndView mainSection(){
        ModelAndView mav = new ModelAndView("page/mainsection/mainsection");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }
    /** 메인섹션 신규, 상세*/
    @GetMapping(value = {"mainsection/{msIdx}","mainpage/new"})
    public ModelAndView mainSection(@PathVariable(required = false) Integer msIdx){
        ModelAndView mav = new ModelAndView("page/mainsection/msIdx");

        return mav;
    }
    /** 메인섹션 배너 신규, 상세*/
    @GetMapping(value = {"mainsection/banner/{msbnIdx}","mainsection/banner/new"})
    public ModelAndView mainSectionBanner(@PathVariable(required = false) Long msbnIdx){
        ModelAndView mav = new ModelAndView("page/mainsection/msbnIdx");

        return mav;
    }
    /** 메인섹션 배너 신규, 상세*/
    @GetMapping(value = {"mainsection/shopProduct/{msspIdx}","mainsection/shopProduct/new"})
    public ModelAndView mainSectionShopProduct(@PathVariable(required = false) Long msspIdx){
        ModelAndView mav = new ModelAndView("page/mainsection/msspIdx");

        return mav;
    }

    /** 이벤트 관리*/
    @GetMapping("event")
    public ModelAndView event(){
        ModelAndView mav = new ModelAndView("page/event");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }

    /** 팝업관리 (임시)*/
    @GetMapping("popup")
    public ModelAndView popup(){
        ModelAndView mav = new ModelAndView("page/popup");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }


}

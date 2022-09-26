package com.newper.controller.view;

import com.newper.constant.MsType;
import com.newper.entity.MainSection;
import com.newper.entity.MainSectionBanner;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.mapper.MainSectionMapper;
import com.newper.mapper.ShopMapper;
import com.newper.repository.MainSectionBannerRepo;
import com.newper.repository.MainSectionRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/design/")
@Controller
@RequiredArgsConstructor
public class DesignController {

    private final ShopRepo shopRepo;
    private final MainSectionRepo mainSectionRepo;
    private final MainSectionBannerRepo mainSectionBannerRepo;
    private final MainSectionMapper mainsectionMapper;
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
    @GetMapping(value = {"mainsection/{msIdx}/{shopIdx}","mainsection/new/{shopIdx}"})
    public ModelAndView mainSection(@PathVariable(required = false) Long msIdx, @PathVariable("shopIdx") Integer shopIdx){
        ModelAndView mav = new ModelAndView("design/mainsection_msIdx");

        if(msIdx != null){
            MainSection mainsection = mainSectionRepo.findById(msIdx).orElseThrow(()-> new MsgException("존재하지 않는 메인섹션입니다."));
            mav.addObject("mainsection", mainsection);
            Map<String,Object> msJson = mainsectionMapper.selectMainSectionMsJson(msIdx);
            mav.addObject("msJson", msJson);

            MainSection mainSetionssss = mainSectionRepo.findMainSectionBymsIdx(msIdx);
        }
        Shop shop = shopRepo.findById(shopIdx).orElseThrow(()-> new MsgException("존재하지 않는 분양몰입니다."));
        mav.addObject("shop", shop);
        return mav;
    }

    /** 메인섹션 상세 메인섹션타입 배너 load */
    @PostMapping(value = {"mainsection/{msIdx}/{shopIdx}/{msType}.load","mainsection/new/{shopIdx}/{msType}.load"})
    public ModelAndView mainSectionBanner(@PathVariable String msType, @PathVariable(required = false) Long msIdx){
        ModelAndView mav = new ModelAndView("design/fragment/mainsection_fragment :: " + msType);
        if(msIdx != null){
            if(msType.equals(MsType.BANNER.name())){
                List<MainSectionBanner> mainSectionBanners = mainSectionBannerRepo.findByMainSection_msIdxOrderByMsbnOrder(msIdx);
                mav.addObject("mainSectionBanners", mainSectionBanners);
            }else if(msType.equals(MsType.PRODUCT.name())){
                List<Map<String,Object>> mainSectionSps = mainsectionMapper.selectMainSectionShopProductByMsIdx(msIdx);
                mav.addObject("mainSectionSps", mainSectionSps);
            }
        }

        return mav;
    }
}

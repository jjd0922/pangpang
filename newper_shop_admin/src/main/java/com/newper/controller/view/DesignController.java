package com.newper.controller.view;

import com.newper.constant.MsType;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.MainSectionMapper;
import com.newper.mapper.ShopMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/design/")
@Controller
@RequiredArgsConstructor
public class DesignController {

    private final ShopRepo shopRepo;
    private final MainSectionRepo mainSectionRepo;
    private final HeaderMenuRepo headerMenuRepo;
    private final HeaderOrderRepository headerOrderRepository;
    private final MainSectionBannerRepo mainSectionBannerRepo;
    private final MainSectionMapper mainsectionMapper;
    private final ShopMapper shopMapper;
    private final ShopCategoryRepo shopCategoryRepo;

    /** 공통 디자인 영역*/
    @GetMapping("")
    public ModelAndView commonDesign(){
        ModelAndView mav = new ModelAndView("design/commonDesign");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }
    /** GNB 메뉴 관리*/
    @GetMapping("headerMenu")
    public ModelAndView mainMenu(){
        ModelAndView mav = new ModelAndView("design/headerMenu");

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

        mav.addObject("hoArr", headerOrderRepository.HeaderOrderArray(shopRepo.getReferenceById(shopIdx)));

        return mav;
    }
    /** GNB 메뉴 신규, 상세*/
    @GetMapping(value = {"headerMenu/{hmIdx}/{shopIdx}","headerMenu/new/{shopIdx}"})
    public ModelAndView headerMenu(@PathVariable(required = false) Integer hmIdx, @PathVariable("shopIdx") Integer shopIdx){
        ModelAndView mav = new ModelAndView("design/headerMenu_hmIdx");

        if(hmIdx != null){
            HeaderMenu headerMenu = headerMenuRepo.findById(hmIdx).orElseThrow(()-> new MsgException("존재하지 않는 메인섹션입니다."));
            mav.addObject("headerMenu", headerMenu);
            mav.addObject("hmIdx", hmIdx);
        }
        Shop shop = shopRepo.findById(shopIdx).orElseThrow(()-> new MsgException("존재하지 않는 분양몰입니다."));
        mav.addObject("shop", shop);
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

//            MainSection mainSetionssss = mainSectionRepo.findMainSectionBymsIdxAndMsType(msIdx, MsType.BANNER.name());
        }
        Shop shop = shopRepo.findById(shopIdx).orElseThrow(()-> new MsgException("존재하지 않는 분양몰입니다."));
        mav.addObject("shop", shop);
        return mav;
    }

    /** 메인섹션 상세 메인섹션타입에 따른 fragment load */
    @PostMapping(value = {"mainsection/{msIdx}/{shopIdx}/{msType}.load","mainsection/new/{shopIdx}/{msType}.load"})
    public ModelAndView mainSectionBanner(@PathVariable String msType, @PathVariable(required = false) Long msIdx, ParamMap paramMap){
        ModelAndView mav = new ModelAndView("design/fragment/mainsection_fragment :: " + msType);
        if(msIdx != null){
            if(msType.equals(MsType.BANNER.name())){
                List<MainSectionBanner> mainSectionBanners = mainSectionBannerRepo.findByMainSection_msIdxOrderByMsbnOrder(msIdx);
                mav.addObject("mainSectionBanners", mainSectionBanners);
            }else if(msType.equals(MsType.PRODUCT.name())){
                List<Map<String,Object>> mainSectionSps = mainsectionMapper.selectMainSectionShopProductByMsIdx(msIdx);
                mav.addObject("mainSectionSps", mainSectionSps);
            }else if(msType.equals(MsType.BOTH.name())){
                List<MainSectionBanner> mainSectionBanners = mainSectionBannerRepo.findByMainSection_msIdxOrderByMsbnOrder(msIdx);
                mav.addObject("mainSectionBanners", mainSectionBanners);
                List<Map<String,Object>> mainSectionSps = mainsectionMapper.selectMainSectionShopProductByMsIdx(msIdx);
                mav.addObject("mainSectionSps", mainSectionSps);
            }else if(msType.equals(MsType.CATEGORY.name()) || msType.equals("categoryProduct")){
                String scateIdx = null;
                if(paramMap.containsKey("scateIdx")){
                    scateIdx = paramMap.getString("scateIdx");
                }
                List<Map<String,Object>> mainSectionSps = mainsectionMapper.selectMainSectionShopProductCategoryByMsIdx(msIdx,scateIdx);
                mav.addObject("mainSectionSps", mainSectionSps);

            }
            List<ShopCategory> shopCategories = shopCategoryRepo.findAllByAndScateOrderGreaterThanOrderByScateOrderAsc(0);
            for(int i=0;i<shopCategories.size();i++){
                Map<String,Object> map = new HashMap<>();
                map.put("scateIdx", shopCategories.get(i).getScateIdx());
                map.put("msIdx",msIdx);

                Map<String,Object> cntMap = mainsectionMapper.selectMainSectionSpCategoryCount(map);
                String cnt = cntMap.get("CNT")+"";
                shopCategories.get(i).setScateName(shopCategories.get(i).getScateName()+"("+cnt+")");
            }
            mav.addObject("shopCategories", shopCategories);
        }

        return mav;
    }
}

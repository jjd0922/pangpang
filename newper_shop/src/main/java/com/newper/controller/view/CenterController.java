package com.newper.controller.view;

import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/custCenter/")
public class CenterController {

    private final ShopRepo shopRepo;
    /** 고객센터 메뉴 로드*/
    @PostMapping("{menu}.load")
    public ModelAndView custCenterMenu(@PathVariable(required = false) String menu){
        ModelAndView mav = new ModelAndView("custCenter/custCenter_menu :: " + menu);

        if(menu.equals("notice")){
            mav.addObject("shopList", shopRepo.findAll());
        }
        return mav;
    }
    /** Faq 메뉴 로드*/
    @PostMapping("faq/list/{menu}.load")
    public ModelAndView custCenterFaqMenu(@PathVariable String menu){
        ModelAndView mav = new ModelAndView("custCenter/custCenterFaqList");

        if(menu.equals("common") || menu.equals("4") || menu.equals("6") || menu.equals("7")){
            mav.addObject("shopList", shopRepo.findAll());
        }

        return mav;
    }


    /** 공지사항 상세*/
    @GetMapping(value = "notice/{ntIdx}")
    public ModelAndView faq(@PathVariable Integer ntIdx){
        ModelAndView mav = new ModelAndView("custCenter/notice_ntIdx");
        return mav;
    }


}

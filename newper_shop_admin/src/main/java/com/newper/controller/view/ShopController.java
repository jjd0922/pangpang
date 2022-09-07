package com.newper.controller.view;

import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/shop/")
@Controller
@RequiredArgsConstructor
public class ShopController {

    private final ShopRepo shopRepo;


    /**분양몰 관리*/
    @GetMapping("")
    public ModelAndView shop(){
        ModelAndView mav = new ModelAndView("shop/shop");

        return mav;
    }
    /**분양몰 신규, 상세 팝업*/
    @GetMapping(value = {"{shopIdx}", "new"})
    public ModelAndView shopIdx(@PathVariable(required = false) Integer shopIdx){
        ModelAndView mav = new ModelAndView("shop/shopIdx");

        //상세
        if(shopIdx != null){
            Shop shop = shopRepo.findById(shopIdx).orElseThrow(()-> new MsgException("존재하지 않는 분양몰입니다."));
            mav.addObject("shop", shop);
        }

        return mav;
    }
    @GetMapping(value = "pop/design/{shopIdx}")
    public ModelAndView shopDesign(@PathVariable Integer shopIdx){
        ModelAndView mav = new ModelAndView("shop/design");


        return mav;
    }



}

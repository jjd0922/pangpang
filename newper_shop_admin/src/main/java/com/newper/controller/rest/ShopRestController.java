package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/shop/")
@RestController
@RequiredArgsConstructor
public class ShopRestController {


    private final ShopService shopService;


    /**상품 관리*/
    @PostMapping("shopSave.ajax")
    public ReturnMap shopSave(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        int res=shopService.shopSave(paramMap);
        if(res>0){
            rm.setMessage("생성");
        }
        return rm;
    }



}

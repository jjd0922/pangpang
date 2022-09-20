package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/myPage/")
public class MyPageRestController {

    private final ShopRepo shopRepo;

    @PostMapping("orders.ajax")
    public ModelAndView orders(ParamMap paramMap){
        ModelAndView mav = new ModelAndView("myPage/myOrderProductList");
        String orderMenuTitle = "";
        List<Shop> shopList = new ArrayList<>();
        if(!paramMap.containsKey("menu")){
            throw new MsgException("잘못된 접근입니다");
        }else if(paramMap.getString("menu").equals("orders")){
            Shop shop = shopRepo.findById(1).orElseThrow(()-> new MsgException("ㄷㄷㄷ"));
            shopList.add(shop);
            orderMenuTitle = "주문 접수";
        }else if(paramMap.getString("menu").equals("payment")){
            shopList = shopRepo.findAll();
            orderMenuTitle = "결제 완료";
        }else if(paramMap.getString("menu").equals("ready")){
            Shop shop = shopRepo.findById(2).orElseThrow(()-> new MsgException("ㄷㄷㄷ"));
            shopList.add(shop);
            orderMenuTitle = "상품 준비중";
        }else if(paramMap.getString("menu").equals("delStart")){
            Shop shop = shopRepo.findById(3).orElseThrow(()-> new MsgException("ㄷㄷㄷ"));
            shopList.add(shop);
            orderMenuTitle = "배송출발";
        }else if(paramMap.getString("menu").equals("delIng")){
            Shop shop = shopRepo.findById(4).orElseThrow(()-> new MsgException("ㄷㄷㄷ"));
            Shop shop2 = shopRepo.findById(3).orElseThrow(()-> new MsgException("www"));
            shopList.add(shop);
            orderMenuTitle = "배송중";
        }else if(paramMap.getString("menu").equals("delComplete")){
            orderMenuTitle = "배송완료";
        }else if(paramMap.getString("menu").equals("cancel")) {
            orderMenuTitle = "취소/교환/반품";
        }else if(paramMap.getString("menu").equals("all")){
            shopList = shopRepo.findAll();
        }
        mav.addObject("shopList", shopList);
        mav.addObject("orderMenuTitle", orderMenuTitle);
        return mav;
    }
}

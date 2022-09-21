package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.entity.Shop;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/custCenter/")
public class CenterRestController {

    private final ShopRepo shopRepo;

    @PostMapping("menu.ajax")
    public String menuTest(ParamMap paramMap, Model m){

        String menu = paramMap.getString("menu");

        List<Shop> shopList = shopRepo.findAll();

        m.addAttribute("shopList",shopList);

//        return "custCenter/custCenter_menu :: "+menu+"("+menu+")";
        return "<th:block th:replace='custCenter/custCenter_menu :: "+menu+"("+menu+")'></th:block>";
//        return "custCenter/custCenter_menu :: #firstDiv";
    }
}

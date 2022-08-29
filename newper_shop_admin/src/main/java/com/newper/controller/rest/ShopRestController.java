package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.Shop;
import com.newper.mapper.ShopMapper;
import com.newper.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/shop/")
@RestController
@RequiredArgsConstructor
public class ShopRestController {


    private final ShopService shopService;
    private final ShopMapper shopMapper;


    /**상품 관리*/
    @PostMapping(value = {"{shopIdx}.ajax", "new.ajax"})
    public ReturnMap shopIdx(@PathVariable(required = false) Integer shopIdx, ParamMap paramMap){
        ReturnMap rm = new ReturnMap();

        paramMap.printEntrySet();
        Shop shop = paramMap.mapParam(Shop.class);
        System.out.println(shop.getShopName());

//        int res=shopService.shopSave(paramMap);
//        if(res>0){
//            rm.setMessage("생성");
//        }
        return rm;
    }

    /**Shop DataTable*/
    @PostMapping("shop.dataTable")
    public ReturnDatatable shop(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable("분양몰 관리");
        List<Map<String, Object>> list = shopMapper.selectShopDatatable(paramMap.getMap());
        returnDatatable.setData(list);
        returnDatatable.setRecordsTotal(shopMapper.countShopDatatable(paramMap.getMap()));
        return returnDatatable;
    }



}

package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.ShopMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/")
@RequiredArgsConstructor
public class ShopRestController {

    private final ShopMapper shopMapper;

    @PostMapping("shop.dataTable")
    public ReturnDatatable shopDatatable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("분양몰");

        rd.setData(shopMapper.selectShopDatatable(paramMap.getMap()));
        rd.setRecordsTotal(shopMapper.countShopDatatable(paramMap.getMap()));
        return rd;
    }

}

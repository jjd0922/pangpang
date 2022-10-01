package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.CalculateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/calculate/")
@RestController
@RequiredArgsConstructor
public class CalculateRestController {
    private final CalculateMapper calculateMapper;


    /** 상품매입정산 */
    @PostMapping(value = "productPurchase.dataTable")
    public ReturnDatatable productPurchase (ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(calculateMapper.selectProductPurchaseDatatable(paramMap.getMap()));
        rd.setRecordsTotal(calculateMapper.countProductPurchaseDatatable(paramMap.getMap()));
        return rd;
    }
}

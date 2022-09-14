package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock/")
public class StockRestController {

    private final LocationMapper locationMapper;

    @PostMapping("stock.dataTable")
    public ReturnDatatable stockInLocation(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("재고현황");
        rd.setData(locationMapper.selectStockInLocationDatatable(paramMap.getMap()));
        rd.setRecordsTotal(locationMapper.countStockInLocationDatatable(paramMap.getMap()));
        return rd;
    }
}

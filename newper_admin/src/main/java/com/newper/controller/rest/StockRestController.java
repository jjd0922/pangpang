package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.LocationMapper;
import com.newper.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock/")
public class StockRestController {

    private final LocationMapper locationMapper;
    private final LocationService locationService;

    @PostMapping("stock.dataTable")
    public ReturnDatatable stockInLocation(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("재고현황");
        rd.setData(locationMapper.selectStockInLocationDatatable(paramMap.getMap()));
        return rd;
    }

    /** 로케이션에 해당하는 자산들 select */
    @PostMapping("/locGoods.ajax")
    public ReturnDatatable locGoods(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("재고현황");
        rd.setData(locationMapper.selectGoodsByLocation(paramMap.getMap()));
        return rd;
    }

    /** 재고인수(적재) */
    @PostMapping("/load/take.ajax")
    public ReturnMap loadTake(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        locationService.saveStockTake(paramMap);
        rm.setMessage("적재완료");
        return rm;
    }

    /** 재고적재(바코드) */
    @PostMapping("/load/barcode.ajax")
    public ReturnMap loadBarcode(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        locationService.saveStockBarcode(paramMap);
        return rm;
    }
}

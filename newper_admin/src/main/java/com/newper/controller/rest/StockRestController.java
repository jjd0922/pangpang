package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.LocationMapper;
import com.newper.mapper.SpecMapper;
import com.newper.mapper.StockMapper;
import com.newper.service.LocationService;
import com.newper.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock/")
public class StockRestController {

    private final LocationMapper locationMapper;
    private final LocationService locationService;

    private final StockService stockService;

    private final SpecMapper specMapper;

/*
    @PostMapping("stock.dataTable")
    public ReturnDatatable stockInLocation(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("재고현황");
        rd.setData(locationMapper.selectStockInLocationDatatable(paramMap.getMap()));
        return rd;
    }
*/

    @PostMapping("stock.dataTable")
    public ReturnDatatable stock(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("재고현황");
        rd.setData(locationMapper.selectStockInLocationDatatable(paramMap.getMap()));
        return rd;
    }

    @PostMapping("stock2.dataTable")
    public ReturnDatatable stock2(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("재고현황");
        System.out.println(paramMap.getMap());

        rd.setData(locationMapper.selectStockInLocationDatatable2(paramMap.getMap()));
        return rd;
    }




    /**재고 자산 상세 스펙이력 데이터 테이블*/
    @PostMapping("spec.dataTable")
    public ReturnDatatable spec(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("스펙리스트");
        rd.setData(specMapper.selectSpecDataTable(paramMap.getMap()));
        rd.setRecordsTotal(specMapper.countSpecDataTable(paramMap.getMap()));
        return rd;
    }


    /** 로케이션에 해당하는 자산들 select */
    @PostMapping("/locGoods.ajax")
    public ReturnDatatable locGoods(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("재고현황");
        rd.setData(locationMapper.selectGoodsByLocation(paramMap.getMap()));
        return rd;
    }
/*

    */
/** 창고이동 등록 팝업 해당하는 자산들 insert *//*

    @PostMapping("/insertGoods.ajax")
    public ReturnDatatable insertGoods(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("재고현황");
        List<String> gIdxs = paramMap.getList("gIdxs[]");
        for(int gIdx : gIdxs){
            locationMapper.insertGoodsByLocation(Integer.parseInt(gIdx));
        }
        rd.setData(locationMapper.insertGoodsByLocation(paramMap.getMap()));
        return rd;
    }

*/



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

    /**
     * 입고등록 발주서 상품 바코드 등록
     */
    @PostMapping("barcode.ajax")
    public ReturnMap barcodeReading(String barcode) {
        ReturnMap rm = new ReturnMap();
        boolean check = stockService.getBarcode(barcode);
        if(!check){
            rm.setMessage("적재된 자산이 없습니다.");
        }else{
            rm.setMessage("바코드 리딩된 자산을 출고전검수요청되었습니다.");
        }

        return rm;
    }



}

package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.SpecMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/goods/")
@RestController
@RequiredArgsConstructor
public class GoodsRestController {
    private final GoodsMapper goodsMapper;
    private final SpecMapper specMapper;

    /** 창고 데이터 테이블 조회 */
    @PostMapping("warehouse.dataTable")
    public ReturnDatatable warehouseDataTable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(goodsMapper.selectWareHouseDataTable(paramMap.getMap()));
        rd.setRecordsTotal(goodsMapper.countWareHouseDataTable(paramMap.getMap()));
        return rd;
    }
    /** 스펙리스트 조회 */
    @GetMapping("specList.ajax")
    public List<String> specList(String specName) {
        return specMapper.selectSpecListValueList(specName);
    }
}

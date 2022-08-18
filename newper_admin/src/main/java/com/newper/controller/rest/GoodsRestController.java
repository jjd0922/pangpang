package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.entity.SpecList;
import com.newper.mapper.GoodsMapper;
import com.newper.repository.SpecListRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/goods/")
@RestController
@RequiredArgsConstructor
public class GoodsRestController {
    private final GoodsMapper goodsMapper;
    private final SpecListRepo specListRepo;

    /** 스펙명으로 스펙리스트 조회 */
    @PostMapping("spec/specSearch.ajax")
    public List<SpecList> specSearch(String specName) {
        return specListRepo.findSpecListBySpeclName(specName);
    }

    /** 창고 데이터 테이블 조회 */
    @PostMapping("warehouse.dataTable")
    public ReturnDatatable warehouseDataTable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(goodsMapper.selectWareHouseDataTable(paramMap.getMap()));
        rd.setRecordsTotal(goodsMapper.countWareHouseDataTable(paramMap.getMap()));
        return rd;
    }
}

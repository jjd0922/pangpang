package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.SpecMapper;
import com.newper.service.GoodsService;
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

    private final GoodsService goodsService;

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

    /** 자산 임시 테이블 조회 */
    @PostMapping("temp.dataTable")
    public ReturnDatatable temp(ParamMap paramMap) {
        ReturnDatatable returnDatatable = new ReturnDatatable("자산 임시 테이블");


        returnDatatable.setData(goodsMapper.selectGoodsTempDatatable(paramMap.getMap()));
        returnDatatable.setRecordsTotal(goodsMapper.countGoodsTempDatatable(paramMap.getMap()));

        return returnDatatable;
    }

    /** 자산 임시 테이블 자산 추가 */
    @PostMapping("ggtGoodsInsert.ajax")
    public ReturnMap ggtGoodsInsert(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        String ggtIdx = paramMap.get("ggtIdx").toString();
        String[] gIdxs = paramMap.get("gIdx").toString().split(",");
        goodsService.insertGoodsTemp(ggtIdx, gIdxs);
        return rm;
    }



    /** 자산 임시 테이블 자산 삭제 */
    @PostMapping("ggtGoodsDelete.ajax")
    public ReturnMap ggtGoodsDelete(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        goodsService.deleteGGTGoods(paramMap);
        rm.setMessage("삭제완료");
        return rm;
    }

    /** 자산 조회 */
    @PostMapping("goods.dataTable")
    public ReturnDatatable goodsDataTable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(goodsMapper.selectGoodsDataTable(paramMap.getMap()));
        rd.setRecordsTotal(goodsMapper.countGoodsDataTable(paramMap.getMap()));
        return rd;
    }
}

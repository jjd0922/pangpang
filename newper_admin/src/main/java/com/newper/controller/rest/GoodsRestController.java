package com.newper.controller.rest;

import com.newper.constant.GgtType;
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
import java.util.Map;

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
    public List<String> specList(String speclName) {
        return specMapper.selectSpecListValueList(speclName);
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
        goodsService.insertGoodsTemp(ggtIdx, GgtType.IN_CHECK);
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
        paramMap.multiSelect("gState");
        rd.setData(goodsMapper.selectGoodsDataTable(paramMap.getMap()));
        rd.setRecordsTotal(goodsMapper.countGoodsDataTable(paramMap.getMap()));
        return rd;
    }

    /** 영업검수에서 반품요청 & 반품반려 처리 */
    @PostMapping("goodsStateUpdateByCheck.ajax")
    public ReturnMap goodsStateUpdateByCheck(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        rm.setMessage(goodsService.goodsStateUpdateByCheck(paramMap));
        return rm;
    }

    /** 영업검수 자산-상품 관계 체크 */
    @PostMapping("checkGoodsProduct.ajax")
    public ReturnDatatable checkGoodsProduct(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(goodsService.checkGoodsProduct(paramMap));
        return rd;
    }


    /** 영업검수 자산-실입고 상품 관계 체크 */
    @PostMapping("checkGoodsReceived.ajax")
    public ReturnDatatable checkGoodsReceived(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(goodsService.checkGoodsReceived(paramMap));
        return rd;
    }


    /** 재검수 확정 */
    @PostMapping("checkGoods.ajax")
    public ReturnMap checkGoods(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        goodsService.checkGoods(paramMap);
        return rm;
    }

    /** 자산 상세 조회 */
    @PostMapping("selectGoods.ajax")
    public Map<String, Object> selectGoods(ParamMap paramMap) {
        return goodsMapper.selectGoodsByG_IDX(paramMap.getLong("gIdx"));
    }

    /** 자산 망실 처리 */
    @PostMapping("updateGoodsLost.ajax")
    public ReturnMap updateGoodsLost(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        goodsService.updateGoodsLost(paramMap);
        return rm;
    }

    /** 해당 자산들 입고검수 요청가능한지 자산값 체크 */
    @PostMapping("goodsInCheck.ajax")
    public ReturnMap goodsInCheck(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        goodsService.goodsInCheck(paramMap);
        return rm;
    }

    /** 해당자산들 재검수인지 자산값 체크 */
    @PostMapping("goodsReCheck.ajax")
    public ReturnMap goodsReCheck(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        goodsService.goodsReCheck(paramMap);
        return rm;
    }

    /** 해당 자산들 해당 공정 가능한지 체크 */
    @PostMapping("goodsProcessCheck.ajax")
    public ReturnMap goodsProcessCheck(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        goodsService.goodsProcessCheck(paramMap);
        return rm;
    }

    /** 자산 반품 가능한지 체크 */
    @PostMapping("goodsResellCheck.ajax")
    public ReturnMap goodsResellCheck(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        goodsService.goodsResellCheck(paramMap);
        return rm;
    }

    /** 자산 상태 변경 */
    @PostMapping("updateGoodsState.ajax")
    public ReturnMap updateGoodsState(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        goodsService.updateGoodsState(paramMap);
        return rm;
    }
}

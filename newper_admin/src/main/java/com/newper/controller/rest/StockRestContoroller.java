package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.LocationMapper;
import com.newper.dto.ReturnMap;
import com.newper.mapper.StockMapper;
import com.newper.service.GoodsService;
import com.newper.service.LocationService;
import com.newper.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/stock/")
@RestController
@RequiredArgsConstructor
public class StockRestContoroller {

    private final StockMapper stockMapper;
    private final LocationMapper locationMapper;
    private final LocationService locationService;
    private final GoodsMapper goodsMapper;
    private final StockService stockService;

    private final GoodsService goodsService;

    /**재고관리 픽킹관리 조회테이블**/
    @PostMapping("parent.dataTable")
    public ReturnDatatable picking() {

        ReturnDatatable returnDatatable = new ReturnDatatable();

        List<Map<String, Object>> pList = stockMapper.selectStockDatatableByParent();
        returnDatatable.setData(pList);
        returnDatatable.setRecordsTotal(pList.size());
        return returnDatatable;
    }


    /**재고관리 픽킹관리 재고코드 클릭시 데이터 조회테이블**/
    @PostMapping("picking2.dataTable")
    public ReturnDatatable picking2(ParamMap paramMap) {
        ReturnDatatable returnDatatable = new ReturnDatatable();

        Integer gs_idx = null;
        if(paramMap.get("GS_IDX")!=null){
            gs_idx=paramMap.getInt("GS_IDX");
        }
        String[] G_STATES = new String[3];
        Map<String, Object> map = new HashMap<>();
        map.put("GS_IDX", gs_idx);
        if(paramMap.get("release")==null){
            G_STATES[0]="STOCK";
            map.put("G_STATES",G_STATES);
        }else{
            G_STATES[0]="BEFORE_RELEASE_REQ";
            G_STATES[1]="BEFORE_RELEASE_IN";
            G_STATES[2]="BEFORE_RELEASE_OUT";
            map.put("G_STATES",G_STATES);
        }
        List<Map<String,Object>> cList = stockMapper.selectStockDatatableByChildren(map);
        returnDatatable.setData(cList);
        returnDatatable.setRecordsTotal(cList.size());

        return returnDatatable;
    }

    /**출고전검수요청*/
    @PostMapping("beforeRelease.ajax")
    public ReturnMap beforeRelease(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        rm.setMessage(stockService.beforeRelease(paramMap));
        return rm;
    }


    /** 재고상품 조회 */
    @PostMapping("stockGoods.dataTable")
    public ReturnDatatable stockGoods(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        rd.setData(stockMapper.selectStockDataTable(paramMap.getMap()));
        rd.setRecordsTotal(stockMapper.countStockDataTable(paramMap.getMap()));

        return rd;
    }

    /** 재고상품 조회 */
    @PostMapping("stockGoodsParts.dataTable")
    public ReturnDatatable stockGoodsParts(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        rd.setData(stockMapper.selectStockParts(paramMap.getMap()));
        rd.setRecordsTotal(stockMapper.countStockParts(paramMap.getMap()));

        return rd;
    }


/*
    *//** 창고이동관리 팝업 출고창고 데이터테이블 *//*
    @PostMapping("whOut.dataTable")
    public ReturnDatatable whOut(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
*//*

        rd.setData(stockMapper.selectStockDataTable(paramMap.getMap()));
        rd.setRecordsTotal(stockMapper.countStockDataTable(paramMap.getMap()));
*//*

        return rd;
    }*/
    /** 창고이동관리 팝업 입고창고 데이터테이블 */
    @PostMapping("whIn.dataTable")
    public ReturnDatatable whIn(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
/*

        rd.setData(stockMapper.selectStockDataTable(paramMap.getMap()));
        rd.setRecordsTotal(stockMapper.countStockDataTable(paramMap.getMap()));
*/

        return rd;
    }
    /** 창고이동관리 팝업 자산상태 데이터테이블 */
    @PostMapping("goods.dataTable")
    public ReturnDatatable goods(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(goodsMapper.selectGoodsDataTable(paramMap.getMap()));
        rd.setRecordsTotal(goodsMapper.countGoodsDataTable(paramMap.getMap()));
        return rd;
    }

}

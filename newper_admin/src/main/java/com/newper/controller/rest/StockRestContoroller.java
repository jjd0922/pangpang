package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.LocationMapper;
import com.newper.dto.ReturnMap;
import com.newper.mapper.SpecMapper;
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

    private final SpecMapper specMapper;

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
        Map<String, Object> map = new HashMap<>();
        map.put("GS_IDX", gs_idx);
        if(paramMap.get("release")==null){
            map.put("G_STOCK_STATE","STOCK");
        }else{
            map.put("G_STOCK_STATE","OUT_REQ");
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


    @PostMapping("/list/barcode.ajax")
    public ReturnMap listBarcode(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        System.out.println(paramMap.getMap());
        List<Map<String, Object>> list=locationService.listStockBarcode(paramMap);
        rm.put("locIdx", paramMap.getString("locIdx"));
        rm.put("barcode", paramMap.getString("barcode").substring(2).split(",").length);
        rm.put("list",list);

        System.out.println(list);

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

    /** 창고이동관리 작업완료 처리 */
    @PostMapping(value = "changeLocation.ajax")
    public ReturnMap changeLocation(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        paramMap.getMap();

        System.out.println("paramMap = " + paramMap);

     /*   int idx = locationService.changeLocation(paramMap);*/

        rm.setMessage("자산상태는 적재로 변경되며 자산적재 로케이션이 변경 됩니다.");

        return rm;
    }


}

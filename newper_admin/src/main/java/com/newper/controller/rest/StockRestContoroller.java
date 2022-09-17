package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.LocationMapper;
import com.newper.mapper.StockMapper;
import com.newper.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/stock/")
@RestController
@RequiredArgsConstructor
public class StockRestContoroller {

    private final StockMapper stockMapper;
    private final LocationMapper locationMapper;
    private final LocationService locationService;

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

        List<Map<String,Object>> cList = stockMapper.selectStockDatatableByChildren(gs_idx);
        returnDatatable.setData(cList);
        returnDatatable.setRecordsTotal(cList.size());

        return returnDatatable;
    }

    /** 재고상품 조회 */
    @PostMapping("stockGoods.dataTable")
    public ReturnDatatable stock(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        rd.setData(stockMapper.selectStockDataTable(paramMap.getMap()));
        rd.setRecordsTotal(stockMapper.countStockDataTable(paramMap.getMap()));

        return rd;
    }
}

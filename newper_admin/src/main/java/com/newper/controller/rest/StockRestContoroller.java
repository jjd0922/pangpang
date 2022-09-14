package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.StockMapper;
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
    @PostMapping("children.dataTable")
    public ReturnDatatable picking2(ParamMap paramMap) {
        ReturnDatatable returnDatatable = new ReturnDatatable();
        Integer GS_IDX=null;
        if(paramMap.get("GS_IDX")!=null&&!paramMap.get("GS_IDX").equals("")){
            GS_IDX=Integer.parseInt(paramMap.get("GS_IDX")+"");
        }
        List<Map<String,Object>> cList = stockMapper.selectStockDatatableByChildren(GS_IDX);
        returnDatatable.setData(cList);
        returnDatatable.setRecordsTotal(cList.size());

        return returnDatatable;
    }
}

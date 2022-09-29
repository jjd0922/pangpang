package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/purchaseSettlement/")
@RestController
@RequiredArgsConstructor
public class PurchaseSettlementRestController {

    /**매입처 데이터테이블 조회*/
    @PostMapping("wheretobuy.dataTable")
    public ReturnDatatable ownDatatable(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("상품매입정산(매입처)");

//        paramMap.multiSelect("sState");
//        paramMap.multiSelect("sType");

//        rd.setData(scheduleMapper.selectScheduleDatatable(paramMap.getMap()));
//        rd.setRecordsTotal(scheduleMapper.countScheduleDatatable(paramMap.getMap()));
        return rd;
    }


}

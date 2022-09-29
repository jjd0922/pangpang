package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/salesSettlement/")
@RestController
@RequiredArgsConstructor
public class SalesSettlementRestController {

    /**자사몰 데이터테이블 조회*/
    @PostMapping("own.dataTable")
    public ReturnDatatable ownDatatable(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("판매매출관리(자사몰)");

//        paramMap.multiSelect("sState");
//        paramMap.multiSelect("sType");

//        rd.setData(scheduleMapper.selectScheduleDatatable(paramMap.getMap()));
//        rd.setRecordsTotal(scheduleMapper.countScheduleDatatable(paramMap.getMap()));
        return rd;
    }


    /**ban 데이터테이블 조회*/
    @PostMapping("ban.dataTable")
    public ReturnDatatable banDatatable(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("BAN(SCM)정산관리");

//        paramMap.multiSelect("sState");
//        paramMap.multiSelect("sType");

//        rd.setData(scheduleMapper.selectScheduleDatatable(paramMap.getMap()));
//        rd.setRecordsTotal(scheduleMapper.countScheduleDatatable(paramMap.getMap()));
        return rd;
    }

}

package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/customer/")
public class CustomerRestController {

    private final CustomerMapper customerMapper;
    
    /**회원관리 데이터테이블 조회*/
    @PostMapping("customer.dataTable")
    public ReturnDatatable customerDatatable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("회원관리");

        paramMap.multiSelect("cuState");
        paramMap.multiSelect("cuRate");
        paramMap.multiSelect("cuGender");

        rd.setData(customerMapper.selectCustomerDatatable(paramMap.getMap()));
        rd.setRecordsTotal(customerMapper.countCustomerDatatable(paramMap.getMap()));
        return rd;
    }
    @PostMapping("order.dataTable")
    public ReturnDatatable test(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        List<Map<String,Object>> testList = new ArrayList<>();
        for(int i=0; i<5; i++){
            Map<String, Object> testMap = new HashMap<>();
            testList.add(testMap);
        }

        rd.setData(testList);
        rd.setRecordsTotal(testList.size());
        return rd;
    }

    @PostMapping("point.dataTable")
    public ReturnDatatable pointTemp(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        List<Map<String,Object>> testList = new ArrayList<>();
        for(int i=0; i<5; i++){
            Map<String, Object> testMap = new HashMap<>();
            testList.add(testMap);
        }

        rd.setData(testList);
        rd.setRecordsTotal(testList.size());
        return rd;
    }

    @PostMapping("coupon.dataTable")
    public ReturnDatatable couponTemp(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        List<Map<String,Object>> testList = new ArrayList<>();
        for(int i=0; i<5; i++){
            Map<String, Object> testMap = new HashMap<>();
            testList.add(testMap);
        }

        rd.setData(testList);
        rd.setRecordsTotal(testList.size());
        return rd;
    }

    @PostMapping("qna.dataTable")
    public ReturnDatatable qnaTemp(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        List<Map<String,Object>> testList = new ArrayList<>();
        for(int i=0; i<5; i++){
            Map<String, Object> testMap = new HashMap<>();
            testList.add(testMap);
        }

        rd.setData(testList);
        rd.setRecordsTotal(testList.size());
        return rd;
    }

    /**발송내역 페이지*/
    @PostMapping("sendingHistory.dataTable")
    public ReturnDatatable sendingHistory(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("발송내역");

        rd.setData(customerMapper.selectSendingHistoryDatatable(paramMap.getMap()));
        rd.setRecordsTotal(customerMapper.countSendingHistoryDatatable(paramMap.getMap()));
        return rd;
    }


}

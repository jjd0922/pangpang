package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

    /**발송내역 페이지*/
    @PostMapping("sendingHistory.dataTable")
    public ReturnDatatable sendingHistory(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("발송내역");
        System.out.println(paramMap.getMap());

        return rd;
    }


}
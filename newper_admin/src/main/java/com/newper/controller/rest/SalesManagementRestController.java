package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/salesManagement/")
@RequiredArgsConstructor
@RestController
public class SalesManagementRestController {

    /**주문관리 배송관리 데이터테이블*/
    @PostMapping("delivery.dataTable")
    public ReturnDatatable delivery (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("배송관리");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }

    /**주문관리 설치관리 데이터테이블*/
    @PostMapping("installation.dataTable")
    public ReturnDatatable installation (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("설치관리");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }
}

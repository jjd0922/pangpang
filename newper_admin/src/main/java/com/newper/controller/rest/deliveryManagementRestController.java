package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.DeliveryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/deliveryManagement/")
@RequiredArgsConstructor
@RestController
public class deliveryManagementRestController {

    private final DeliveryMapper deliveryMapper;
    /**배송관리 데이터테이블*/
    @PostMapping("delivery.dataTable")
    public ReturnDatatable delivery (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("배송관리");

        rd.setData(deliveryMapper.selectDeliveryDatatable(paramMap.getMap()));
        rd.setRecordsTotal(deliveryMapper.countDeliveryDatatable(paramMap.getMap()));
        return rd;
    }
    /**배송관리 데이터테이블2*/
    @PostMapping("delivery2.dataTable")
    public ReturnDatatable delivery2 (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("배송관리");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }
    /**설치관리 데이터테이블*/
    @PostMapping("installation.dataTable")
    public ReturnDatatable installation (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("설치관리");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }

    /**배송관리 송장통합팝업 데이터테이블*/
    @PostMapping("invoiceIntegrated.dataTable")
    public ReturnDatatable invoiceIntegrated (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("송장통합");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }

    /**배송관리 송장등록 데이터테이블*/
    @PostMapping("invoiceUpload.dataTable")
    public ReturnDatatable invoiceUpload (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("송장등록");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }
    /**배송관리 출고등록 데이터테이블*/
    @PostMapping("release1.dataTable")
    public ReturnDatatable release1 (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("출고등록 주문내역");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }

    /**배송관리 출고등록 데이터테이블*/
    @PostMapping("release2.dataTable")
    public ReturnDatatable release2 (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("출고등록 자산내역");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }
}

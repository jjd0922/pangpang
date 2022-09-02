package com.newper.controller.rest;


import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.CalculateMapper;
import com.newper.repository.CalculateSettingRepo;
import com.newper.service.PurchaseCalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/purchaseCal/")
@RestController
@RequiredArgsConstructor
public class PurchaseCalRestController {


    private final CalculateMapper calculateMapper;
    private final PurchaseCalService purchaseCalService;

    /**벤더정산 관리페이지 조회테이블**/
    @PostMapping("vendor.dataTable")
    public ReturnDatatable vendor(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("벤더정산");

        paramMap.multiSelect("CS_TYPE");

        rd.setData(calculateMapper.selectCalculateDatatable(paramMap.getMap()));
        rd.setRecordsTotal(calculateMapper.countCalculateDatatable(paramMap.getMap()));
        return rd;
    }

    /**벤더정산팝업 저장*/
    @PostMapping(value = "vendorCreate.ajax")
    public ReturnMap vendorCreate(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        int idx = purchaseCalService.saveVendor(paramMap);

        rm.setMessage("등록이 완료되었습니다.");

        return rm;
    }

}

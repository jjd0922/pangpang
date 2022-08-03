package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.CompanyMapper;
import com.newper.repository.CompanyEmployeeRepo;
import com.newper.repository.CompanyRepo;
import com.newper.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/company/")
@RestController
@RequiredArgsConstructor
public class CompanyRestController {
    private final CompanyMapper companyMapper;
    private final CompanyRepo companyRepo;
    private final CompanyEmployeeRepo companyEmployeeRepo;
    private final CompanyService companyService;

    /**거래처 관리 데이터테이블*/
    @PostMapping("company.dataTable")
    public ReturnDatatable company(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        paramMap.multiSelect("ctType");
        paramMap.multiSelect("comType");
        paramMap.multiSelect("comState");

        rd.setData(companyMapper.selectCompanyDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countCompanyDatatable(paramMap.getMap()));

        return rd;
    }

    @PostMapping("contract.dataTable")
    public ReturnDatatable companyContract(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        paramMap.multiSelect("ccState");
        paramMap.multiSelect("ccType");
        paramMap.multiSelect("ccCalType");
        paramMap.multiSelect("ccCycle");

        rd.setData(companyMapper.selectCompanyContract(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countCompanyContract(paramMap.getMap()));
        return rd;
    }

    /**카테고리별 입점사 수수료관리 페이지 > 입점사 목록 가져오기*/
    @PostMapping("store.dataTable")
    public ReturnDatatable store(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        rd.setData(companyMapper.selectStoreDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countStoreDatatable(paramMap.getMap()));
        return rd;
    }


    /**카테고리별  입점사 수수료관리 페이지 > 카테고리별 수수료 데이터테이블*/
    @PostMapping("fee.dataTable")
    public ReturnDatatable fee(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        String comIdx = paramMap.get("comIdx").toString();
        System.out.println("comIdx = " + comIdx);

        paramMap.multiSelect("cfType");

        rd.setData(companyMapper.selectFeeDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countFeeDatatable(paramMap.getMap()));

        return rd;
    }
}

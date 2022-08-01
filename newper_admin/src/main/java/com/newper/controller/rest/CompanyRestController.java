package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.Company;
import com.newper.entity.CompanyEmployee;
import com.newper.mapper.CompanyMapper;
import com.newper.repository.CompanyEmployeeRepo;
import com.newper.repository.CompanyRepo;
import com.newper.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping(value = "/company/")
@RestController
@RequiredArgsConstructor
public class CompanyRestController {
    private final CompanyMapper companyMapper;
    private final CompanyRepo companyRepo;
    private final CompanyEmployeeRepo companyEmployeeRepo;
    private final CompanyService companyService;

    /** 거래처 관리 데이터테이블 */
    @PostMapping("company.dataTable")
    public ReturnDatatable company(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();

        paramMap.multiSelect("ctType");
        paramMap.multiSelect("comType");
        paramMap.multiSelect("comState");

        rd.setData(companyMapper.selectCompanyDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countCompanyDatatable(paramMap.getMap()));

        return rd;
    }

    /** 거래처 수정 처리 */
    @PostMapping("modify/{comIdx}")
    public ReturnMap modify(@PathVariable Integer comIdx, ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        System.out.println("comIdx = " + comIdx);
        ReturnMap rm = new ReturnMap();


        companyService.updateCompany(comIdx, paramMap);
        return rm;
    }

    @PostMapping("contract.dataTable")
    public ReturnDatatable companyContract(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();

        paramMap.multiSelect("ccState");
        paramMap.multiSelect("ccType");
        paramMap.multiSelect("ccCalType");
        paramMap.multiSelect("ccCycle");

        rd.setData(companyMapper.selectCompanyContract(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countCompanyContract(paramMap.getMap()));
        return rd;
    }

}

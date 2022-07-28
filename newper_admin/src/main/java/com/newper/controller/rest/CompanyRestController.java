package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.Company;
import com.newper.mapper.CompanyMapper;
import com.newper.repository.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequestMapping(value = "/company/")
@RestController
@RequiredArgsConstructor
public class CompanyRestController {
    private final CompanyMapper companyMapper;
    private final CompanyRepo companyRepo;

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

    @PostMapping("contract.dataTable")
    public ReturnDatatable companyContract(@RequestParam Map<String, Object> param){
        ReturnDatatable rd = new ReturnDatatable();
        System.out.println("contract: " + companyMapper.selectCompanyContract(param));

        rd.setData(companyMapper.selectCompanyContract(param));
        rd.setRecordsTotal(companyMapper.countCompanyContract(param));


        return rd;
    }

}

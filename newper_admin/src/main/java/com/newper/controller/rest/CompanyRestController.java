package com.newper.controller.rest;

import com.newper.constant.ComState;
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
    public ReturnDatatable company(@RequestParam Map<String, Object> map){
        ReturnDatatable rd = new ReturnDatatable();
        System.out.println("company list: " + companyMapper.selectCompany());

        rd.setData(companyMapper.selectCompany());
        rd.setRecordsTotal(companyMapper.countCompany());

        return rd;
    }

    @PostMapping("regist.ajax")
    public ReturnMap registCompany(ParamMap paramMap) {
        System.out.println("regist param: " + paramMap.entrySet());

        Company company = paramMap.mapParam(Company.class);
        companyRepo.save(company);

        System.out.println("Test");

        return null;
    }

    @PostMapping("contract.dataTable")
    public ReturnDatatable companyContract(@RequestParam Map<String, Object> param){
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(companyMapper.selectCompanyContract(param));
        rd.setRecordsTotal(companyMapper.countCompanyContract(param));

        return rd;
    }

}

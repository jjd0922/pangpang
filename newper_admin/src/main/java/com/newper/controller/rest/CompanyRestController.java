package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.CompanyMapper;
import com.newper.repository.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(value = "/company/")
@RestController
@RequiredArgsConstructor
public class CompanyRestController {
    private final CompanyMapper companyMapper;

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
        rd.setData(companyMapper.selectCompanyContract(param));
        rd.setRecordsTotal(companyMapper.countCompanyContract(param));

        return rd;
    }

}

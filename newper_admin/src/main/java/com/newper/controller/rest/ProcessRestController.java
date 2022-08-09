package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/process/")
@RestController
@RequiredArgsConstructor
public class ProcessRestController {
    private final CompanyMapper companyMapper;


    @PostMapping("modal.dataTable")
    public ReturnDatatable modal(ParamMap paramMap) {

        ReturnDatatable rd = new ReturnDatatable();


        rd.setData(companyMapper.selectCompanyDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countCompanyDatatable(paramMap.getMap()));
        return rd;
    }
}

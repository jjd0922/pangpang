package com.newper.controller.rest;

import com.newper.dto.ReturnDatatable;
import com.newper.mapper.CompanyMapper;
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

    /** 거래처 관리 데이터테이블 */
    @PostMapping("company.dataTable")
    public ReturnDatatable company(@RequestParam Map<String, Object> map){
        ReturnDatatable rd = new ReturnDatatable();

        List<Map<String, Object>> data = new ArrayList<>();
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());

        rd.setData(data);
        rd.setRecordsTotal(120);

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

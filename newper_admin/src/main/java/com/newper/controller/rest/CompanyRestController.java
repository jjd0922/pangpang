package com.newper.controller.rest;

import com.newper.dto.ReturnDatatable;
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

}

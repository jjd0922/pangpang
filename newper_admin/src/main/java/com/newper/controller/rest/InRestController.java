package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.PoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequestMapping(value = "/in/")
@RestController
@RequiredArgsConstructor
public class InRestController {

    private final PoMapper poMapper;

    @PostMapping("in.dataTable")
    public ReturnDatatable company(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        List<Map<String, Object>> data = poMapper.selectInDatatable(paramMap.getMap());
        int count = poMapper.countInDatatable(paramMap.getMap());

        rd.setData(data);
        rd.setRecordsTotal(count);

        return rd;
    }
}

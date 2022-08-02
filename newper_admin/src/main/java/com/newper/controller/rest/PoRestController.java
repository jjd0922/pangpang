package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.PoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/po/")
@RestController
@RequiredArgsConstructor
public class PoRestController {
    private final PoMapper poMapper;

    /** 거래처 관리 데이터테이블 */
    @PostMapping("estimate.dataTable")
    public ReturnDatatable estimate(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        paramMap.multiSelect("peState");
        rd.setData(poMapper.selectEstimateDataTable(paramMap.getMap()));
        rd.setRecordsTotal(poMapper.countEstimateDataTable(paramMap.getMap()));
        return rd;
    }
}

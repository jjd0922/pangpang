package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.BomMapper;
import com.newper.service.BomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/bom/")
@RestController
@RequiredArgsConstructor
public class BomRestController {

    private final BomMapper bomMapper;
    private final BomService bomService;

    @PostMapping("bom.dataTable")
    public ReturnDatatable bomDataTable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("BOM관리");

        rd.setData(bomMapper.selectBomDatatable(paramMap.getMap()));
        rd.setRecordsTotal(bomMapper.countBomDatatable(paramMap.getMap()));
        return rd;
    }

    @PostMapping("bomPop.ajax")
    public ReturnMap saveBom(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        bomService.saveBom(paramMap);
        rm.setMessage("등록완료");
        return rm;
    }

}

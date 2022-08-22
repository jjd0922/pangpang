package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/bom/")
@RestController
@RequiredArgsConstructor
public class BomRestController {

    @PostMapping("bom.dataTable")
    public ReturnDatatable bomDataTable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("BOM관리");


        return rd;
    }
}

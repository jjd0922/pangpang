package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/warehouse/")
@RestController
@RequiredArgsConstructor
public class WarehouseRestController {

    private final WarehouseMapper warehouseMapper;

    @PostMapping("warehouse.dataTable")
    public ReturnDatatable warehouseDatatable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("창고관리");

        rd.setData(warehouseMapper.selectWarehouseDatatable(paramMap.getMap()));
        rd.setRecordsTotal(warehouseMapper.countWarehouseDatatable(paramMap.getMap()));
        return rd;
    }
}

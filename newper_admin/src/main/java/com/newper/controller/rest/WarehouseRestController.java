package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.WarehouseMapper;
import com.newper.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/warehouse/")
@RestController
@RequiredArgsConstructor
public class WarehouseRestController {

    private final WarehouseMapper warehouseMapper;
    private final WarehouseService warehouseService;

    @PostMapping("warehouse.dataTable")
    public ReturnDatatable warehouseDatatable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("창고관리");

        rd.setData(warehouseMapper.selectWarehouseDatatable(paramMap.getMap()));
        rd.setRecordsTotal(warehouseMapper.countWarehouseDatatable(paramMap.getMap()));
        return rd;
    }

    @PostMapping("warehousePop.ajax")
    public ReturnMap saveWarehouse(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        Integer whIdx = warehouseService.saveWarehouse(paramMap);

        rm.setMessage("등록완료");
        rm.setLocation("/warehouse/warehousePop/" + whIdx);
        return rm;
    }

    @PostMapping("warehousePop/{whIdx}.ajax")
    public ReturnMap updateWarehouse(@PathVariable Integer whIdx, ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        warehouseService.updateWarehouse(whIdx, paramMap);

        rm.setMessage("수정완료");
        return rm;
    }
}

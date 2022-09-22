package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.LocationMapper;
import com.newper.mapper.WarehouseMapper;
import com.newper.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "/warehouse/")
@RestController
@RequiredArgsConstructor
public class WarehouseRestController {

    private final WarehouseMapper warehouseMapper;
    private final WarehouseService warehouseService;
    private final LocationMapper locationMapper;

    /**창고 데이터테이블 조회*/
    @PostMapping("warehouse.dataTable")
    public ReturnDatatable warehouseDatatable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("창고관리");

        paramMap.multiSelect("whState");
        rd.setData(warehouseMapper.selectWarehouseDatatable(paramMap.getMap()));
        rd.setRecordsTotal(warehouseMapper.countWarehouseDatatable(paramMap.getMap()));
        return rd;
    }

    /**창고정보 신규등록*/
    @PostMapping("warehousePop.ajax")
    public ReturnMap saveWarehouse(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        Integer whIdx = warehouseService.saveWarehouse(paramMap);

        rm.setMessage("등록완료");
        rm.setLocation("/warehouse/warehousePop/" + whIdx);
        return rm;
    }

    /**창고정보 수정*/
    @PostMapping("warehousePop/{whIdx}.ajax")
    public ReturnMap updateWarehouse(@PathVariable Integer whIdx, ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        warehouseService.updateWarehouse(whIdx, paramMap);

        rm.setMessage("수정완료");
        return rm;
    }

    /**창고 상태 일괄변경*/
    @PostMapping("changeWhState.ajax")
    public ReturnMap changeWhState(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        warehouseService.changeWhState(paramMap);
        rm.setMessage("일괄변경완료");
        return rm;
    }

    /**로케이션 데이터테이블 조회*/
    @PostMapping("location.dataTable")
    public ReturnDatatable locationDatatable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        paramMap.multiSelect("locType");
        paramMap.multiSelect("locForm");

        rd.setData(locationMapper.selectLocationDatatable(paramMap.getMap()));
        rd.setRecordsTotal(locationMapper.countLocationDatatable(paramMap.getMap()));
        return rd;
    }

    /**로케이션 신규등록*/
    @PostMapping("locationPop/{whIdx}.ajax")
    public ReturnMap saveLocation(@PathVariable Integer whIdx, ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        Integer locIdx = warehouseService.saveLocation(whIdx, paramMap);
        rm.setMessage("등록완료");
        rm.setLocation("/warehouse/locationPop/" + whIdx + "/" + locIdx);
        return rm;
    }

    /**로케이션정보 수정*/
    @PostMapping("locationPop/{whIdx}/{locIdx}.ajax")
    public ReturnMap updateLocation(@PathVariable Integer whIdx,
                                    @PathVariable Integer locIdx,
                                    ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        warehouseService.updateLocation(locIdx, paramMap);
        rm.setMessage("수정완료");
        rm.setLocation("/warehouse/locationPop/"+whIdx+"/"+locIdx);
        return rm;
    }

    /**로케이션 구분 일괄변경*/
    @PostMapping("changeLocType.ajax")
    public ReturnMap changeLocType(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        warehouseService.changeLocType(paramMap);
        rm.setMessage("일괄변경완료");
        return rm;
    }

    /**로케이션 엑셀 업로드*/
    @PostMapping("excelUpload.ajax")
    public ReturnMap uploadLocationByExcel (ParamMap paramMap, MultipartFile excelFile) {
        ReturnMap rm = new ReturnMap();

        String result = warehouseService.uploadLocationByExcel(paramMap, excelFile);

        if (result == "") {
            rm.put("result","로케이션 엑셀 업로드 완료");
        } else {
            rm.put("result", result);
        }
        return rm;
    }
}

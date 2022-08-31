package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.ScheduleMapper;
import com.newper.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "/schedule/")
@RestController
@RequiredArgsConstructor
public class ScheduleRestController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    /**영업활동 데이터테이블 조회*/
    @PostMapping("schedule.dataTable")
    public ReturnDatatable scheduleDatatable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        paramMap.multiSelect("sState");
        paramMap.multiSelect("sType");

        rd.setData(scheduleMapper.selectScheduleDatatable(paramMap.getMap()));
        rd.setRecordsTotal(scheduleMapper.countScheduleDatatable(paramMap.getMap()));
        return rd;
    }
    
    /**영업활동정보 등록*/
    @PostMapping("schedulePop.ajax")
    public ReturnMap saveSchedule(ParamMap paramMap, MultipartFile sFile1, MultipartFile sFile2, MultipartFile sFile3) {
        ReturnMap rm = new ReturnMap();

        Integer sIdx = scheduleService.saveSchedule(paramMap, sFile1, sFile2, sFile3);
        rm.setMessage("등록완료");
        rm.setLocation("/schedule/schedulePop/"+sIdx);
        return rm;
    }

    /**영업활동정보 수정*/
    @PostMapping("schedulePop/{sIdx}.ajax")
    public ReturnMap updateSchedule(@PathVariable Integer sIdx, ParamMap paramMap, MultipartFile sFile1, MultipartFile sFile2, MultipartFile sFile3) {
        ReturnMap rm = new ReturnMap();

        Integer newSIdx = scheduleService.updateSchedule(sIdx, paramMap, sFile1, sFile2, sFile3);

        rm.setMessage("수정완료");
        rm.setLocation("/schedule/schedulePop/"+newSIdx);
        return rm;
    }
}

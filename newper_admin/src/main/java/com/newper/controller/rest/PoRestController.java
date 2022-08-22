package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.exception.MsgException;
import com.newper.mapper.PoMapper;
import com.newper.service.PoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/po/")
@RestController
@RequiredArgsConstructor
public class PoRestController {
    private final PoMapper poMapper;
    private final PoService poService;

    /** 거래처 관리 데이터테이블 */
    @PostMapping("estimate.dataTable")
    public ReturnDatatable estimate(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        paramMap.multiSelect("peState");
        rd.setData(poMapper.selectEstimateDataTable(paramMap.getMap()));
        rd.setRecordsTotal(poMapper.countEstimateDataTable(paramMap.getMap()));
        return rd;
    }
    /** 발주 품의 데이터테이블 */
    @PostMapping("po.dataTable")
    public ReturnDatatable po(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable("발주품의");

        rd.setData(poMapper.selectPoDataTable(paramMap.getMap()));
        rd.setRecordsTotal(poMapper.countPoDataTable(paramMap.getMap()));

        return rd;
    }
    /** 발주품의 생성 */
    @PostMapping(value = "poPop.ajax")
    public ReturnMap poPopPost(ParamMap paramMap, MultipartFile poFile){
        ReturnMap rm = new ReturnMap();

        Integer poIdx = poService.savePo(paramMap, poFile);

        rm.setLocation("/po/poPop/" + poIdx);
        rm.setMessage("발주품의 등록 완료");

        return rm;
    }
}

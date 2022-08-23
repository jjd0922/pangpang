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

    /** 입고등록 조회*/
    @PostMapping("in.dataTable")
    public ReturnDatatable in(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("입고등록");

        List<Map<String, Object>> data = poMapper.selectInDatatable(paramMap.getMap());
        int count = poMapper.countInDatatable(paramMap.getMap());

        rd.setData(data);
        rd.setRecordsTotal(count);

        return rd;
    }
    /** 입고등록 팝업에서 발주서 group by 상품 조회*/
    @PostMapping("po.dataTable")
    public ReturnDatatable po(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        List<Map<String, Object>> data = poMapper.selectInPoDatatable(paramMap.getMap());
        rd.setData(data);
        rd.setRecordsTotal();

        return rd;
    }
    /** 입고등록 팝업에서 상품 클릭시 나오는 발주상품index 자산 목록 조회*/
    @PostMapping("pp.dataTable")
    public ReturnDatatable pp(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        List<Map<String, Object>> data = poMapper.selectInPpDatatable(paramMap.getMap());
        int count = poMapper.countInPpDatatable(paramMap.getMap());

        rd.setData(data);
        rd.setRecordsTotal(count);

        return rd;
    }
}

package com.newper.controller.rest;

import com.newper.dto.ReturnDatatable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/design/")
@RestController
@RequiredArgsConstructor
public class DesignRestController {

    @PostMapping(value = "test.dataTable")
    public ReturnDatatable testDt(){
        ReturnDatatable rd = new ReturnDatatable();
        List<Map<String,Object>> testList = new ArrayList<>();
        for(int i=0; i < 10;i++){
            Map<String,Object> testMap = new HashMap<>();

            testList.add(testMap);
        }
        rd.setData(testList);
        rd.setRecordsTotal(testList.size());
        return rd;
    }
}

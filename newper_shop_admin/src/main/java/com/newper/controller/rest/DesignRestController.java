package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.service.DesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final DesignService designService;

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
    /** 디자인 update*/
    @PostMapping(value = "pop/design/{shopIdx}.ajax")
    public ReturnMap shopDesignUpdate(@PathVariable Integer shopIdx, ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        paramMap.put("shopIdx", shopIdx);

        designService.shopDesignUpdate(paramMap);

        rm.setMessage("수정 완료");
        rm.put("loc", "/design/pop/design/"+shopIdx);

        return rm;
    }


}

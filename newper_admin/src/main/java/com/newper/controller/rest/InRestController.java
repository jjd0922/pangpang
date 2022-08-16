package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequestMapping(value = "/in/")
@RestController
@RequiredArgsConstructor
public class InRestController {

    @PostMapping("in.dataTable")
    public ReturnDatatable company(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        List list = new ArrayList<>();
        list.add(new HashMap());
        list.add(new HashMap());
        list.add(new HashMap());
        list.add(new HashMap());
        rd.setData(list);

        return rd;
    }
}

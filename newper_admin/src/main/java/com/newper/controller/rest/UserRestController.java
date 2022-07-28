package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.User;
import com.newper.repository.UserRepo;
import com.newper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping(value = "/user/")
@RestController
@RequiredArgsConstructor
public class UserRestController {
    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private final UserService userService;

    @PostMapping("user.dataTable")
    public ReturnDatatable user(@RequestParam Map<String, Object> map) {
        ReturnDatatable rd = new ReturnDatatable();

        List<Map<String, Object>> data = new ArrayList<>();

        rd.setData(data);
        rd.setRecordsTotal(List.of().size());

        return rd;
    }

    @PostMapping("modal.dataTable")
    public ReturnDatatable modal(@RequestParam Map<String, Object> map) {
        ReturnDatatable rd = new ReturnDatatable();

        List<Map<String, Object>> data = new ArrayList<>();

        rd.setData(data);
        rd.setRecordsTotal(List.of().size());

        return rd;
    }

}


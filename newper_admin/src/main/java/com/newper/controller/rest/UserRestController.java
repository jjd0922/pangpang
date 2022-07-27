package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
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
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());
        data.add(new HashMap<>());

        rd.setData(data);
        rd.setRecordsTotal(120);

        return rd;
    }


    @PostMapping("/userCreate.ajax")
    public String userCreate(ParamMap pmap) {
        System.out.println("map. = " + pmap.getMap());

        user
        userRepo.save();

        return "";
    }
}

/*
    @RequestMapping(value = "userCreate.ajax", method = {RequestMethod.POST})
    public String userCreate(@RequestParam ) {


        System.out.println("vo.entrySet() = " + vo.entrySet());
        return "redirect:/";
    }
}*/

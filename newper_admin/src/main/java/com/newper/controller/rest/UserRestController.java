package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.Company;
import com.newper.entity.User;
import com.newper.mapper.CompanyMapper;
import com.newper.mapper.UserMapper;
import com.newper.repository.CompanyRepo;
import com.newper.repository.UserRepo;
import com.newper.service.CompanyService;
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

    private final UserRepo userRepo;


    private final UserService userService;

    private final CompanyService companyService;


    private final UserMapper userMapper;

    private final CompanyMapper companyMapper;

    private final CompanyRepo companyRepo;

    @PostMapping("user.dataTable")
    public ReturnDatatable user(ParamMap paramMap) {

        ReturnDatatable rd = new ReturnDatatable();


        rd.setData(userMapper.selectUserDatatable(paramMap.getMap()));
        rd.setRecordsTotal(userMapper.countUserDatatable(paramMap.getMap()));
        return rd;
    }

    @PostMapping("modal.dataTable")
    public ReturnDatatable modal(ParamMap paramMap) {

        ReturnDatatable rd = new ReturnDatatable();


        rd.setData(companyMapper.selectCompanyDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countCompanyDatatable(paramMap.getMap()));
        return rd;
    }


    @PostMapping("/userCreate.ajax")
    public ReturnMap userCreate(ParamMap paramMap) {
        ReturnMap rm= new ReturnMap();
        System.out.println("paramMap= " + paramMap.getMap());

        userService.userCreate(paramMap);

        rm.setMessage("등록완료");
        return rm;
    }

    @PostMapping("searchCompany.ajax")
    public ReturnMap searchCompany(ParamMap paramMap){
        System.out.println("paramMap = " + paramMap);
        ReturnMap rm =new ReturnMap();

        String comIdx = paramMap.get("COM_IDXS").toString();
        //    Integer comIdx =(Integer) paramMap.get("COM_IDX");
        System.out.println("COM_IDX = " + comIdx);
        String comIdxss[] = paramMap.get("COM_IDXS").toString().split(",");
        for(int i=0; i<comIdxss.length; i++){

            Optional<Company> company1 = companyRepo.findById(Integer.parseInt(comIdxss[i]));
            Company company = company1.get();

            rm.put("com_idx",company.getComIdx());
            rm.put("com_name",company.getComName());
        }


        System.out.println(rm);

        return rm;
    }

}


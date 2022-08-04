package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.Company;
import com.newper.entity.User;
import com.newper.exception.MsgException;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
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


    /**사용자 관리페이지 조회테이블**/
    @PostMapping("user.dataTable")
    public ReturnDatatable user(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("사용자관리");


        rd.setData(userMapper.selectUserDatatable(paramMap.getMap()));
        rd.setRecordsTotal(userMapper.countUserDatatable(paramMap.getMap()));
        return rd;
    }
    /**사용자등록 상호법인명 검색 모달**/
    @PostMapping("modal.dataTable")
    public ReturnDatatable modal(ParamMap paramMap) {

        ReturnDatatable rd = new ReturnDatatable();


        rd.setData(companyMapper.selectCompanyDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countCompanyDatatable(paramMap.getMap()));
        return rd;
    }
    /**모달창 상호법인명 검색처리**/
    @PostMapping("searchCompany.ajax")
    public ReturnMap searchCompany(ParamMap paramMap) {
        System.out.println("paramMap = " + paramMap);
        ReturnMap rm = new ReturnMap();
        int COM_IDX = Integer.parseInt(paramMap.get("COM_IDX") + "");
        Optional<Company> company = companyRepo.findById(COM_IDX);
        rm.put("com_idx", company.get().getComIdx());
        rm.put("com_name", company.get().getComName());


        return rm;
    }

    /** 사용자 신규등록 처리 */
    @PostMapping(value = "userCreate.ajax")
    public ReturnMap userInsert(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        String uName = paramMap.getString("U_NAME");
        System.out.println(paramMap.getMap());
        if (uName == null || uName.equals("")) {
            throw new MsgException("이름을 입력해주세요.");
        }
        String uPhone = paramMap.getString("U_PHONE");
        if (uPhone == null || uPhone.equals("")) {
            throw new MsgException("휴대폰번호를 입력해주세요.");
        }
        String comName = paramMap.getString("COM_NAME");
        if (comName == null || comName.equals("")) {
            throw new MsgException("상호법인명을 입력해주세요.");
        }

        String uState = paramMap.getString("U_STATE");
        if (uState == null || uState.equals("")) {
            throw new MsgException("상태를 선택해주세요.");
        }
        String uId = paramMap.getString("U_ID");
        if (uId == null || uId.equals("")) {
            throw new MsgException("로그인 ID를 입력해주세요.");
        }
        String authIdx = paramMap.getString("U_AUTH_IDX");
        if (authIdx == null || authIdx.equals("")) {
            throw new MsgException("권한을 입력해주세요.");
        }
        String uBirth = paramMap.getString("U_BIRTH");
        if ( uBirth.equals("")) {
            uBirth=null;
            paramMap.put("U_BIRTH",uBirth);

        }

        int idx = userService.saveUser(paramMap);

        rm.setMessage(idx + "");




        return rm;
    }


}


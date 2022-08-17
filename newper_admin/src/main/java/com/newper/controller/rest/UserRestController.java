package com.newper.controller.rest;

import com.newper.constant.UType;
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
    /** 사용자 신규등록 처리 */
    @PostMapping(value = "userCreate.ajax")
    public ReturnMap userInsert(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        int idx = userService.saveUser(paramMap);

        rm.setMessage("등록이 완료되었습니다.");

        return rm;
    }


    @PostMapping("resetPwd.ajax")
    public ReturnMap resetPwd(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        System.out.println("parammap = " + paramMap);

        // 비밀번호 초기화
        String resetPwd = User.getRandomPassword(8);

        // pw 업데이트
        Integer uIdx = Integer.parseInt(paramMap.get("U_IDX").toString());
        System.out.println("uIdx = " + uIdx);
        userService.userUpdatePwd(uIdx,resetPwd);

        // rm
        rm.put("resetPwd", resetPwd);

        return rm;
    }

    /**사용자 모달 데이터테이블*/
    @PostMapping("userModal.dataTable")
    public ReturnDatatable searchUser(ParamMap paramMap) {
        System.out.println("paramMap = " + paramMap);
        ReturnDatatable rd = new ReturnDatatable();

        paramMap.put("U_TYPE", UType.INSIDE.name());
        rd.setData(userMapper.selectUserForCompany(paramMap.getMap()));
        rd.setRecordsTotal(userMapper.countUserForCompany(paramMap.getMap()));

        return rd;
    }

}


package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.CompanyMapper;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/process/")
@RestController
@RequiredArgsConstructor
public class ProcessRestController {
    private final CompanyMapper companyMapper;

    private final UserMapper userMapper;
    private final GoodsMapper goodsMapper;

    /** 공정보드 조회 */
    @PostMapping("board.dataTable")
    public ReturnDatatable board(ParamMap paramMap) {
        ReturnDatatable returnDatatable = new ReturnDatatable("공정보드");

        returnDatatable.setData(goodsMapper.selectProcessBoardDatatable(paramMap.getMap()));
        returnDatatable.setRecordsTotal(goodsMapper.countProcessBoardDatatable(paramMap.getMap()));

        return returnDatatable;
    }
    /**팝업창 내부 작업지시자(내부) 모달**/
    @PostMapping("user.dataTable")
    public ReturnDatatable user(ParamMap paramMap) {

        ReturnDatatable rd = new ReturnDatatable();

        rd.setData(userMapper.selectUserDatatable(paramMap.getMap()));
        rd.setRecordsTotal(userMapper.countUserDatatable(paramMap.getMap()));
        return rd;
    }


    /**가공관리 페이지 조회테이블**/
    @PostMapping("processing.dataTable")
    public ReturnDatatable processing(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("가공관리");

        //임의로 userMapper 사용
        rd.setData(userMapper.selectUserDatatable(paramMap.getMap()));
        rd.setRecordsTotal(userMapper.countUserDatatable(paramMap.getMap()));
        return rd;
    }

    /**매입처반품 페이지 조회테이블**/
    @PostMapping("purchasingReturn.dataTable")
    public ReturnDatatable purchasingReturn(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("매입처반품");

        //임의로 userMapper 사용
        rd.setData(userMapper.selectUserDatatable(paramMap.getMap()));
        rd.setRecordsTotal(userMapper.countUserDatatable(paramMap.getMap()));
        return rd;
    }
    /**수리관리 페이지 조회테이블**/
    @PostMapping("fix.dataTable")
    public ReturnDatatable fix(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("수리관리");

        //임의로 userMapper 사용
        rd.setData(userMapper.selectUserDatatable(paramMap.getMap()));
        rd.setRecordsTotal(userMapper.countUserDatatable(paramMap.getMap()));
        return rd;
    }

    /**도색관리 페이지 조회테이블**/
    @PostMapping("paint.dataTable")
    public ReturnDatatable paint(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("도색관리");

        //임의로 userMapper 사용
        rd.setData(userMapper.selectUserDatatable(paramMap.getMap()));
        rd.setRecordsTotal(userMapper.countUserDatatable(paramMap.getMap()));
        return rd;
    }
    /**재검수관리 페이지 조회테이블**/
    @PostMapping("recheck.dataTable")
    public ReturnDatatable recheck(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("재검수관리");

        //임의로 userMapper 사용
        rd.setData(userMapper.selectUserDatatable(paramMap.getMap()));
        rd.setRecordsTotal(userMapper.countUserDatatable(paramMap.getMap()));
        return rd;
    }

}

package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/board/")
@RestController
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardMapper boardMapper;

    /**공지사항 DataTable*/
    @PostMapping("shop.dataTable")
    public ReturnDatatable shop(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable("공지사항 관리");
        List<Map<String, Object>> list = boardMapper.selectNoticeDatatable(paramMap.getMap());
        rd.setData(list);
        rd.setRecordsTotal(boardMapper.countNoticeDatatable(paramMap.getMap()));
        return rd;
    }
}

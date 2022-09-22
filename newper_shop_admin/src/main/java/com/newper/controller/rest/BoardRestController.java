package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.BoardMapper;
import com.newper.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final BoardService boardService;

    /**공지사항 DataTable*/
    @PostMapping("notice.dataTable")
    public ReturnDatatable shop(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable("공지사항 관리");
        List<Map<String, Object>> list = boardMapper.selectNoticeDatatable(paramMap.getMap());
        rd.setData(list);
        rd.setRecordsTotal(boardMapper.countNoticeDatatable(paramMap.getMap()));
        return rd;
    }

    /** 공지사항 등록/수정 */
    @PostMapping(value = {"notice/new.ajax", "notice/{ntIdx}.ajax"})
    public ReturnMap saveNotice(@PathVariable(value = "ntIdx", required = false) Long ntIdx, ParamMap paramMap){
        ReturnMap rm = new ReturnMap();

        paramMap.put("ntIdx", ntIdx);
        boardService.saveNotice(paramMap);

        if(ntIdx != null){
            rm.setMessage("수정완료");
        }else{
            rm.setMessage("등록완료");
        }
        return rm;
    }
}

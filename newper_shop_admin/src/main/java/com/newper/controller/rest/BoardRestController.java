package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.BoardMapper;
import com.newper.mapper.EventGroupMapper;
import com.newper.service.BoardService;
import com.newper.service.EventGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/board/")
@RestController
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardMapper boardMapper;
    private final EventGroupMapper eventGroupMapper;
    private final BoardService boardService;
    private final EventGroupService eventGroupService;

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
    /** 이벤트그룹 dataTables*/
    @PostMapping("eventGroup.dataTable")
    public ReturnDatatable event(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable("이벤트/기획전 관리");
        List<Map<String, Object>> list = eventGroupMapper.selectEventGroupDatatable(paramMap.getMap());
        rd.setData(list);
        rd.setRecordsTotal(eventGroupMapper.countEventGroupDatatable(paramMap.getMap()));
        return rd;
    }

    /** 이벤트그룹 등록/수정 */
    @PostMapping(value = {"event/new/{shopIdx}.ajax", "event/{egIdx}/{shopIdx}.ajax"})
    public ModelAndView saveEventGroup(@PathVariable(value = "egIdx",required = false) Long egIdx, @PathVariable(value = "shopIdx") Integer shopIdx, ParamMap paramMap, MultipartHttpServletRequest mfRequest){
        ModelAndView mav = new ModelAndView("main/alertMove");

        if(egIdx != null){
            paramMap.put("egIdx",egIdx);
            eventGroupService.eventGroupUpdate(paramMap, mfRequest);
            mav.addObject("msg","수정 완료");
        }else{
            egIdx = eventGroupService.eventGroupSave(paramMap, mfRequest);
            mav.addObject("msg","생성 완료");
        }
        mav.addObject("loc","/board/event/"+egIdx+"/"+shopIdx);

        return mav;
    }
    /** 이벤트그룹 노출/미노출 토글 */
    @PostMapping("event/toggle.ajax")
    public ReturnMap toggleEventGroup(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        eventGroupService.toggleEventGroup(paramMap);
        return rm;
    }
    /** 이벤트 그룹 일괄삭제 */
    @PostMapping("event/delete.ajax")
    public ReturnMap deleteEventGroup(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        eventGroupService.deleteEventGroup(paramMap);
        rm.setMessage("삭제 완료");
        return rm;
    }
}

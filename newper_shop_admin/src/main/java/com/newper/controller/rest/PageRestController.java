package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.MainsectionMapper;
import com.newper.mapper.ShopMapper;
import com.newper.service.MainsectionService;
import com.newper.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/page/")
@RestController
@RequiredArgsConstructor
public class PageRestController {

    private final MainsectionService mainsectionService;
    private final MainsectionMapper mainsectionMapper;

    /** mainsection DataTable*/
    @PostMapping("mainsection.dataTable")
    public ReturnDatatable mainsection(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable("메인섹션 관리");

        List<Map<String, Object>> list = mainsectionMapper.selectMainSectionDatatable(paramMap.getMap());
        Map<String,Object> countMap = mainsectionMapper.countMainSectionDatatable(paramMap.getMap());

        rd.setData(list);
        rd.setRecordsTotal(Long.parseLong(String.valueOf(countMap.get("CNT"))));
        return rd;
    }
    /** mainsection insert/update */
    @PostMapping(value = {"mainsection/{msIdx}.ajax", "mainsection/new.ajax"})
    public ReturnMap mainsectionInsertUpdate(@PathVariable(required = false) Long msIdx, ParamMap paramMap){
        ReturnMap rm = new ReturnMap();

        if(msIdx != null){
            paramMap.put("msIdx",msIdx);
            mainsectionService.mainsectionUpdate(paramMap);
            rm.setMessage("수정 완료");
        }else{
            mainsectionService.mainsectionSave(paramMap);
            rm.setMessage("생성 완료");
        }

        return rm;
    }
    /** mainsection 순서 변경*/
    @PostMapping("mainsection.ajax")
    public ReturnMap mainsectionOrder(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        List<String> msIdxs = paramMap.getList("msIdxs[]");

        mainsectionService.mainsectionOrder(msIdxs);

        return rm;
    }
    /** mainsection delete*/
    @PostMapping("mainsection/delete.ajax")
    public ReturnMap mainsectionDelete(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        mainsectionService.mainsectionDelete(paramMap);

        rm.setMessage("삭제 완료");
        return rm;
    }

}

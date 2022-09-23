package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.MainsectionMapper;
import com.newper.service.DesignService;
import com.newper.service.MainsectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/design/")
@RestController
@RequiredArgsConstructor
public class DesignRestController {

    private final DesignService designService;

    @PostMapping(value = "test.dataTable")
    public ReturnDatatable testDt(){
        ReturnDatatable rd = new ReturnDatatable();
        List<Map<String,Object>> testList = new ArrayList<>();
        for(int i=0; i < 10;i++){
            Map<String,Object> testMap = new HashMap<>();

            testList.add(testMap);
        }
        rd.setData(testList);
        rd.setRecordsTotal(testList.size());
        return rd;
    }
    /** 디자인 update*/
    @PostMapping(value = "pop/design/{shopIdx}.ajax")
    public ReturnMap shopDesignUpdate(@PathVariable Integer shopIdx, ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        paramMap.put("shopIdx", shopIdx);

        designService.shopDesignUpdate(paramMap);

        rm.setMessage("수정 완료");
        rm.put("loc", "/design/pop/design/"+shopIdx);

        return rm;
    }



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
    /** mainsection 노출상태 토글 */
    @PostMapping("mainsection/toggle.ajax")
    public ReturnMap mainsectionDisplayToggle(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        mainsectionService.mainsectionDisplayToggle(paramMap);

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

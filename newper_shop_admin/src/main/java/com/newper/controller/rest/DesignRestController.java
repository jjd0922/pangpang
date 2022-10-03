package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.exception.MsgException;
import com.newper.mapper.HeaderMenuMapper;
import com.newper.mapper.MainSectionMapper;
import com.newper.service.DesignService;
import com.newper.service.HeaderMenuService;
import com.newper.service.MainsectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/design/")
@RestController
@RequiredArgsConstructor
public class DesignRestController {

    private final DesignService designService;
    private final MainsectionService mainsectionService;
    private final HeaderMenuService headerMenuService;
    private final MainSectionMapper mainsectionMapper;
    private final HeaderMenuMapper headerMenuMapper;

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
        rm.setLocation("/design/pop/design/"+shopIdx);

        return rm;
    }
    /** 헤더 update*/
    @PostMapping(value = "pop/header/{shopIdx}.load")
    public ModelAndView shopHeader(@PathVariable Integer shopIdx, ParamMap paramMap){
        ModelAndView mav = new ModelAndView("main/alertMove");
        paramMap.put("shopIdx", shopIdx);
        designService.shopHeaderUpdate(paramMap);

        mav.addObject("msg", "수정 완료");
        mav.addObject("loc", "/design/pop/header/"+shopIdx);
        return mav;
    }

    /** mainsection DataTable*/
    @PostMapping("mainsection.dataTable")
    public ReturnDatatable mainsection(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable("메인섹션 관리");

        if(!paramMap.containsKey("shopIdx") || paramMap.get("shopIdx").equals("")){
            throw new MsgException("분양몰을 선택해주세요.");
        }

        List<Map<String, Object>> list = mainsectionMapper.selectMainSectionDatatable(paramMap.getMap());
        Map<String,Object> countMap = mainsectionMapper.countMainSectionDatatable(paramMap.getMap());

        rd.setData(list);
        rd.setRecordsTotal(Long.parseLong(String.valueOf(countMap.get("CNT"))));
        return rd;
    }
    /** mainsection insert/update */
    @PostMapping(value = {"mainsection/{msIdx}/{shopIdx}.load", "mainsection/new/{shopIdx}.load"})
    public ModelAndView mainsectionInsertUpdate(@PathVariable(required = false) Long msIdx,@PathVariable("shopIdx") Integer shopIdx, ParamMap paramMap, MultipartHttpServletRequest mfRequest){
        ModelAndView mav = new ModelAndView("main/alertMove");

        if(msIdx != null){
            paramMap.put("msIdx",msIdx);
            mainsectionService.mainsectionUpdate(paramMap, mfRequest);
            mav.addObject("msg","수정 완료");
        }else{
            msIdx = mainsectionService.mainsectionSave(paramMap, mfRequest);
            mav.addObject("msg","생성 완료");
        }
        mav.addObject("loc","/design/mainsection/"+msIdx+"/"+shopIdx);

        return mav;
    }
    /** mainsection 순서 변경*/
    @PostMapping("mainsection/order.ajax")
    public ReturnMap mainsectionOrder(long[] idxs){
        ReturnMap rm = new ReturnMap();
        mainsectionService.mainsectionOrder(idxs);
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

    /** GNB 메뉴 dataTables*/
    @PostMapping("headerMenu.dataTable")
    public ReturnDatatable mainMenuDataTables(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable("GNB메뉴 관리");

        if(!paramMap.containsKey("shopIdx") || paramMap.get("shopIdx").equals("")){
            throw new MsgException("분양몰을 선택해주세요.");
        }

        List<Map<String, Object>> list = headerMenuMapper.selectMainMenuDatatable(paramMap.getMap());
        Map<String,Object> countMap = headerMenuMapper.countMainMenuDatatable(paramMap.getMap());

        rd.setData(list);
        rd.setRecordsTotal(Long.parseLong(String.valueOf(countMap.get("CNT"))));
        return rd;
    }
    /** GNB 메뉴 insert/update */
    @PostMapping(value = {"headerMenu/{hmIdx}/{shopIdx}.ajax","headerMenu/new/{shopIdx}.ajax"})
    public ReturnMap headerMenuInsertUpdate(@PathVariable(required = false) Integer hmIdx, @PathVariable("shopIdx") Integer shopIdx, ParamMap paramMap){
        ReturnMap rm = new ReturnMap("");

        if(hmIdx != null){
            paramMap.put("hmIdx",hmIdx);
            headerMenuService.headerMenuUpdate(paramMap);
            rm.setMessage("수정 완료");
        }else{
            hmIdx = headerMenuService.headerMenuSave(paramMap);
            rm.setMessage("생성 완료");
        }
        rm.setLocation("/design/headerMenu/"+hmIdx+"/"+shopIdx);
        return rm;
    }


    /** GNB 메뉴(헤더메뉴) 순서 변경*/
    @PostMapping("headerMenu/order.ajax")
    public ReturnMap mainMenuOrder(Integer[] idxs){
        ReturnMap rm = new ReturnMap();
        headerMenuService.headerMenuOrder(idxs);
        return rm;
    }

    /** GNB 메뉴(헤더메뉴) 노출상태 토글 */
    @PostMapping("headerMenu/toggle.ajax")
    public ReturnMap headerMenuDisplayToggle(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        headerMenuService.headerMenuDisplayToggle(paramMap);
        return rm;
    }

    /** GNB 메뉴 delete*/
    @PostMapping("headerMenu/delete.ajax")
    public ReturnMap headerMenuDelete(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        String msg = headerMenuService.headerMenuDelete(paramMap);

        rm.setMessage(msg);
        return rm;
    }
}

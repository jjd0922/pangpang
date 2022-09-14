package com.newper.controller.rest;

import com.newper.component.SessionInfo;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.exception.MsgException;
import com.newper.mapper.PoMapper;
import com.newper.service.PoService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/po/")
@RestController
@RequiredArgsConstructor
public class PoRestController {
    private final PoMapper poMapper;
    private final PoService poService;

    @Autowired
    private final SessionInfo sessionInfo;

    /** 거래처 관리 데이터테이블 */
    @PostMapping("estimate.dataTable")
    public ReturnDatatable estimate(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        paramMap.multiSelect("peState");
        rd.setData(poMapper.selectEstimateDataTable(paramMap.getMap()));
        rd.setRecordsTotal(poMapper.countEstimateDataTable(paramMap.getMap()));
        return rd;
    }
    /** 발주 품의 데이터테이블 */
    @PostMapping("po.dataTable")
    public ReturnDatatable po(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable("발주품의");
        paramMap.multiSelect("poState");
        rd.setData(poMapper.selectPoDataTable(paramMap.getMap()));
        rd.setRecordsTotal(poMapper.countPoDataTable(paramMap.getMap()));

        return rd;
    }
    /** 발주품의 생성 */
    @PostMapping(value = "poPop.ajax")
    public ReturnMap poPopPost(ParamMap paramMap, MultipartFile poFile){
        ReturnMap rm = new ReturnMap();

        Integer poIdx = poService.savePo(paramMap, poFile);

        rm.setLocation("/po/poPop/" + poIdx);
        rm.setMessage("발주품의 등록 완료");

        return rm;
    }

    /** 발주품의 수정 */
    @PostMapping(value = "poPop/{poIdx}/poPop.ajax")
    public ReturnMap poPopDetailPost(@PathVariable long poIdx, ParamMap paramMap, MultipartFile poFile){
        ReturnMap rm = new ReturnMap();
        poService.updatePo(poIdx, paramMap, poFile);
        rm.setLocation("/po/poPop/" + poIdx);
        rm.setMessage("발주품의 수정 완료");

        return rm;
    }

    /** 발주 하이웍스 승인요청 */
    @PostMapping(value = "poHiworks.ajax")
    public ReturnMap poHiworksAjax(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        rm.setMessage(poService.poHiworks(paramMap, sessionInfo));
        return rm;
    }

    /** 발주 하이웍스 승인 & 반려 응답 */
    @PostMapping(value = "poHiworksResponse.ajax")
    public void poHiworksResponseAjax(ParamMap paramMap){
        poService.poHiworksResponse(paramMap);
    }

    /** 발주 삭제 */
    @PostMapping(value = "poDelete.ajax")
    public ReturnMap poDeleteAjax(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        rm.setMessage(poService.poDelete(paramMap));

        return rm;
    }

    /** 발주 관리 데이터테이블 */
    @PostMapping("approved.dataTable")
    public ReturnDatatable approved(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable("발주관리");

        rd.setData(poMapper.selectPoApprovedDatatable(paramMap.getMap()));
        rd.setRecordsTotal(poMapper.countPoApprovedDatatable(paramMap.getMap()));

        return rd;
    }

    /** 발주관리 승인&취소 */
    @PostMapping("poStateUpdate.ajax")
    public ReturnMap poStateUpdate(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        rm.setMessage(poService.poStateUpdate(paramMap));
        return rm;
    }

    /** 발주 상품 조회 */
    @PostMapping("selectPoProduct.dataTable")
    public ReturnDatatable selectPoProduct(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(poMapper.selectPoProductByPoIdx(paramMap.getInt("poIdx")));
        rd.setRecordsTotal(poMapper.countPoProductByPoIdx(paramMap.getInt("poIdx")));
        return rd;
    }

}

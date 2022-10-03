package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.CalculateMapper;
import com.newper.service.CalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/calculate/")
@RestController
@RequiredArgsConstructor
public class CalculateRestController {
    private final CalculateMapper calculateMapper;
    private final CalculateService calculateService;


    /** 정산 그룹 조회 */
    @PostMapping(value = "calculateGroup.dataTable")
    public ReturnDatatable calculateGroup (ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        paramMap.multiSelect("ccgAdjust");
        paramMap.multiSelect("ccgState");
        paramMap.multiSelect("ccgCloseState");
        paramMap.multiSelect("ccgCalType");

        rd.setData(calculateMapper.selectCalculateGroupDatatable(paramMap.getMap()));
        rd.setRecordsTotal(calculateMapper.countCalculateGroupDatatable(paramMap.getMap()));
        return rd;
    }

    /** 매출정산 조회 **/
    @PostMapping(value = "orderGs.dataTable")
    public ReturnDatatable orderGs (ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();

        rd.setData(calculateMapper.selectOrderGsDatatable(paramMap.getMap()));
        rd.setRecordsTotal(calculateMapper.countOrderGsDatatable(paramMap.getMap()));
        return rd;
    }

    /** 정산상태 수정 */
    @PostMapping(value = "updateConfirmState.ajax")
    public ReturnMap updateConfirmState (ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        calculateService.updateConfirmState(paramMap);
        rm.setMessage("정산처리완료");
        return rm;
    }

    /** 정산상태 일과 수정 */
    @PostMapping(value = "updateConfirmStateArr.ajax")
    public ReturnMap updateConfirmStateArr (ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        calculateService.updateConfirmStateArr(paramMap);
        rm.setMessage("정산처리완료");
        return rm;
    }

    /** 마감상태 수정 */
    @PostMapping(value = "updateCloseState.ajax")
    public ReturnMap updateCloseState (ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        calculateService.updateCloseState(paramMap);
        rm.setMessage("마감처리완료");
        return rm;
    }

    /** 마감상태 일괄 수정 */
    @PostMapping(value = "updateCloseStateArr.ajax")
    public ReturnMap updateCloseStateArr (ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        calculateService.updateCloseStateArr(paramMap);
        rm.setMessage("마감처리완료");
        return rm;
    }

    /** 정산조정 저장 */
    @PostMapping(value = "saveAdjust.ajax")
    public ReturnMap saveAdjust (ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        calculateService.saveAdjust(paramMap);
        rm.setMessage("조정완료");
        return rm;
    }

    /** 정산조정 삭제 */
    @PostMapping(value = "removeAdjust.ajax")
    public ReturnMap removeAdjust (ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        calculateService.removeAdjust(paramMap);
        rm.setMessage("삭제완료");
        return rm;
    }

    /** 매출 정산 처리 */
    @PostMapping(value = "updateConfirmStateSales.ajax")
    public ReturnMap updateConfirmStateSales (ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        calculateService.updateConfirmStateSales(paramMap);
        rm.setMessage("정산처리완료");
        return rm;
    }

    /** 매출 마감 처리 */
    @PostMapping(value = "updateCloseStateSales.ajax")
    public ReturnMap updateCloseStateSales (ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        calculateService.updateCloseStateSales(paramMap);
        rm.setMessage("마감처리완료");
        return rm;
    }

    /** 매출정산 조정금액 조회 */
    @PostMapping(value = "selectAdjust.ajax")
    public List<Map<String, Object>> selectAdjust (ParamMap paramMap){
        return calculateMapper.selectAdjust(paramMap.getMap());
    }

    /**벤더정산 팝업 수정처리*/
    @PostMapping("vendorPop/{csIdx}")
    public ModelAndView updateVendor(@PathVariable Integer csIdx, ParamMap paramMap){
        ModelAndView mav = new ModelAndView("main/alertMove");

        calculateService.updateVendor(paramMap,csIdx);
        mav.addObject("msg", "수정 완료");
        mav.addObject("loc", "calculate/vendorPop" + csIdx);
        return mav;
    }

    /**벤더정산 관리페이지 조회테이블**/
    @PostMapping("vendor.dataTable")
    public ReturnDatatable vendor(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("벤더정산");
        paramMap.multiSelect("CS_TYPE");

        rd.setData(calculateMapper.selectVendorSettingDatatable(paramMap.getMap()));
        rd.setRecordsTotal(calculateMapper.countVendorSettingDatatable(paramMap.getMap()));
        return rd;
    }

    /**벤더정산팝업 저장*/
    @PostMapping(value = "vendorCreate.ajax")
    public ReturnMap vendorCreate(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        int idx = calculateService.saveVendor(paramMap);

        rm.setMessage("등록이 완료되었습니다.");

        return rm;
    }

    /**벤더정산팝업 삭제*/
    @PostMapping(value = "deleteVendor.ajax")
    public ReturnMap deleteVendor (ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        calculateService.deleteVendor(paramMap);
        rm.setMessage("삭제 완료");
        return rm;
    }

    /** 벤더사 정산 엑셀 업로드 **/
    @PostMapping(value = "uploadVendor.ajax")
    public ReturnMap uploadVendor (ParamMap paramMap, MultipartFile excelFile) throws IOException {
        ReturnMap rm = new ReturnMap();
        calculateService.uploadVendor(paramMap, excelFile);
        rm.setMessage("정산 업로드 완료");
        return rm;
    }

}

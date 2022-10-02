package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.CalculateMapper;
import com.newper.service.CalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

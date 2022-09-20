package com.newper.controller.rest;

import com.newper.constant.GgtType;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.CompanyMapper;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.ProcessMapper;
import com.newper.mapper.UserMapper;
import com.newper.repository.ProcessSpecRepo;
import com.newper.service.CheckService;
import com.newper.service.GoodsService;
import com.newper.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/process/")
@RestController
@RequiredArgsConstructor
public class ProcessRestController {
    private final CompanyMapper companyMapper;

    private final UserMapper userMapper;
    private final GoodsMapper goodsMapper;
    private final GoodsService goodsService;
    private final CheckService checkService;
    private final ProcessMapper processMapper;
    private final ProcessService processService;

    /** 공정보드 조회 */
    @PostMapping("board.dataTable")
    public ReturnDatatable board(ParamMap paramMap) {
        ReturnDatatable returnDatatable = new ReturnDatatable("공정보드");

        paramMap.multiSelect("gState");

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
    public ReturnDatatable processing(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("가공관리");
        rd.setData(processMapper.selectProcessGroupDatatable(paramMap.getMap()));
        rd.setRecordsTotal(processMapper.countProcessGroupDatatable(paramMap.getMap()));
        return rd;
    }

    /**매입처반품 페이지 조회테이블**/
    @PostMapping("purchasingReturn.dataTable")
    public ReturnDatatable purchasingReturn(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("매입처반품");

        //임의로 userMapper 사용
        rd.setData(processMapper.selectResellDatatable(paramMap.getMap()));
        rd.setRecordsTotal(processMapper.countResellDatatable(paramMap.getMap()));
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
    /** 입고검수 임시 테이블. return ggt_idx*/
    @PostMapping("in/temp.ajax")
    public String inTemp(ParamMap paramMap){
        String idx = paramMap.getString("idx");
        String g_idxs = paramMap.getString("g_idxs");
        GgtType ggtType = GgtType.valueOf(paramMap.getString("type"));
        return goodsService.insertGoodsTemp(idx, g_idxs.split(","), ggtType);
    }
    /**입고검수 그룹 등록*/
    @PostMapping("incheckPop.ajax")
    public ReturnMap incheckPop(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        checkService.insertCheckGroup(paramMap);
        rm.setMessage("입고검수 요청 완료");
        return rm;
    }

    @PostMapping("needPop.ajax")
    public ReturnMap process(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        checkService.insertProcessGroup(paramMap);
        rm.setMessage("공정 요청 완료");
        return rm;
    }

    /** 공정필요 자산 select */
    @PostMapping("selectProcessNeed.ajax")
    public ReturnDatatable selectProcessNeed(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(processMapper.selectProcessNeed(paramMap.getMap()));
        return rd;
    }

    /** 공정스펙 select */
    @PostMapping("selectProcessSpec.ajax")
    public ReturnDatatable selectProcessSpec(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        paramMap.multiSelect("psType");
        rd.setData(processMapper.selectProcessSpec(paramMap.getMap()));
        return rd;
    }

    /** 공정 - 가공 데이터 생성 */
    @PostMapping("saveProcessSpec.ajax")
    public ReturnMap saveProcessSpec(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        processService.saveProcessSpec(paramMap);
        rm.setMessage("가공SPEC 확정 완료");
        return rm;
    }

    /** 공정 스픅 데이터 삭제 */
    @PostMapping("deleteProcessSpec.ajax")
    public ReturnMap deleteProcessSpec(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        processService.deleteProcessSpec(paramMap);
        rm.setMessage("공정 내역 삭제 완료");
        return rm;
    }

    /** 공정결과 업로드 */
    @PostMapping("saveProcess.ajax")
    public ReturnMap saveProcess(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        processService.saveProcess(paramMap);
        rm.setMessage("공정 업로드 완료");
        return rm;
    }

    /** 반품그룹 생성 */
    @PostMapping("resellPop.ajax")
    public ReturnMap resellPop(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        processService.resellPop(paramMap);
        rm.setMessage("반품그룹 생성 완료");
        return rm;
    }

    /** 반품 - 자산 조회 */
    @PostMapping("selectGoodsResell.dataTable")
    public ReturnDatatable selectGoodsResell(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(processMapper.selectGoodsResell(paramMap.getMap()));
        rd.setRecordsTotal(processMapper.countGoodsResell(paramMap.getMap()));
        return rd;
    }

    /** 반품완료 처리 */
    @PostMapping("resellComp.ajax")
    public ReturnMap resellComp(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        processService.resellComp(paramMap);
        rm.setMessage("자산 반품 완료");
        return rm;
    }
}

package com.newper.controller.view;

import com.newper.constant.PgType;
import com.newper.constant.PnType;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.mapper.ProcessMapper;
import com.newper.repository.CheckGroupRepo;
import com.newper.repository.ProcessGroupRepo;
import com.newper.repository.ProcessNeedRepo;
import com.newper.repository.ResellRepo;
import com.newper.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
@RequestMapping(value = "/process/")
@RequiredArgsConstructor
public class ProcessController {
    private final ProcessGroupRepo processGroupRepo;
    private final ProcessNeedRepo processNeedRepo;
    private final ProcessMapper processMapper;
    private final ResellRepo resellRepo;
    private final CheckGroupRepo checkGroupRepo;
    private final ProcessService processService;


    /**공정관리 공정보드 페이지**/
    @GetMapping(value = "board")
    public ModelAndView board() {
        ModelAndView mav = new ModelAndView("process/board");

        return mav;
    }


    /**입고검수 팝업**/
    @GetMapping(value = "incheckPop")
    public ModelAndView incheckPopup() {
        ModelAndView mav = new ModelAndView("process/incheckPop");
        return mav;
    }

    /**가공요청 팝업**/
    @GetMapping(value = "needPop")
    public ModelAndView needPopup(String type) {
        ModelAndView mav = new ModelAndView("process/needPop");
        mav.addObject("type", PnType.valueOf(type));
        return mav;
    }

    /**재검수요청 팝업**/
    @GetMapping(value = "recheckPop")
    public ModelAndView recheckPopup() {
        ModelAndView mav = new ModelAndView("process/recheckPop");

        return mav;
    }
    /**망실 모달**/
    @GetMapping(value = "lostModal")
    public ModelAndView lostModal() {
        ModelAndView mav = new ModelAndView("process/modal/lostModal");

        return mav;
    }
    /**재고인계 모달**/
    @GetMapping(value = "handoverModal")
    public ModelAndView handoverModal() {
        ModelAndView mav = new ModelAndView("process/modal/handoverModal");

        return mav;
    }

    /**매입처반품 페이지**/
    @GetMapping(value = "purchasingReturn")
    public ModelAndView purchasingReturn() {
        ModelAndView mav = new ModelAndView("process/purchasingReturn");

        return mav;
    }


    /**매입처반품 반품관리팝업**/
    @GetMapping(value = "returnManagePop/{rsIdx}")
    public ModelAndView returnManagePop(@PathVariable int rsIdx) {
        ModelAndView mav = new ModelAndView("process/returnManagePop");
        mav.addObject("resell", resellRepo.findById(rsIdx).get());
        return mav;
    }

    /**반품관리 작업요청취소 모달**/
    @GetMapping(value = "calcelModal")
    public ModelAndView calcelModal() {
        ModelAndView mav = new ModelAndView("process/modal/calcelModal");

        return mav;
    }

    /**반품관리 작업중(출고)모달**/
    @GetMapping(value = "ingModal")
    public ModelAndView ingModal() {
        ModelAndView mav = new ModelAndView("process/modal/ingModal");

        return mav;
    }


    /**반품관리 작업완료(입고)모달**/
    @GetMapping(value = "finishModal")
    public ModelAndView finishModal() {
        ModelAndView mav = new ModelAndView("process/modal/finishModal");

        return mav;
    }

    /**반품관리 반품완료 모달**/
    @GetMapping(value = "finishReModal")
    public ModelAndView finishReModal() {
        ModelAndView mav = new ModelAndView("process/modal/finishReModal");

        return mav;
    }

    /**반품관리 반품불가 모달**/
    @GetMapping(value = "impossibleReModal")
    public ModelAndView impossibleReModal() {
        ModelAndView mav = new ModelAndView("process/modal/impossibleReModal");

        return mav;
    }

    /**반품관리 반품결과업로드 모달**/
    @GetMapping(value = "uploadReModal")
    public ModelAndView uploadReModal() {
        ModelAndView mav = new ModelAndView("process/modal/uploadReModal");

        return mav;
    }

    /**공정관리 페이지**/
    @GetMapping(value = "processing")
    public ModelAndView processing(String type) {
        ModelAndView mav = new ModelAndView("process/processing");
        type = type.toUpperCase();
        mav.addObject("type", PgType.valueOf(type));
        return mav;
    }

    /**재검수관리 페이지**/
    @GetMapping(value = "recheck")
    public ModelAndView recheck() {
        ModelAndView mav = new ModelAndView("process/recheck");

        return mav;
    }

    /** 공정 - 수리, 도색 그룹 상세 */
    @GetMapping("processGroupPop/{pgIdx}")
    public ModelAndView processGroupPop(@PathVariable int pgIdx) {
        ModelAndView mav = new ModelAndView("process/processGroupPop");
        mav.addObject("group", processGroupRepo.findByPgIdx(pgIdx));
        return mav;
    }


    /** 공정 - 가공관리 그룹 상세 */
    @GetMapping("processGroupPop_process/{pgIdx}")
    public ModelAndView processGroupPop_process(@PathVariable int pgIdx) {
        ModelAndView mav = new ModelAndView("process/processGroupPop_process");
        mav.addObject("group", processGroupRepo.findByPgIdx(pgIdx));
        return mav;
    }

    /** 반품 요청 팝업 */
    @GetMapping(value = "resellPop")
    public ModelAndView resellPop(ParamMap paramMap) {
        ModelAndView mav = new ModelAndView("process/resellPop");
        mav.addObject("company", processMapper.selectResellComIdx(paramMap.getMap()));
        return mav;
    }

    /** 재검수 상세 팝업 */
    @GetMapping(value = "checkGroupPop/{cgIdx}")
    public ModelAndView checkGroupPop(@PathVariable int cgIdx) {
        ModelAndView mav = new ModelAndView("process/checkGroupPop");
        mav.addObject("check", checkGroupRepo.findCheckGroupByCgIdx(cgIdx));
        return mav;
    }

    /** 검수 - 자산 정보 업데이트 */
    @PostMapping(value = "updateCheckGoods.ajax")
    public ReturnMap updateCheckGoods(ParamMap paramMap, MultipartFile[] cgsFile) {
        ReturnMap rm = new ReturnMap();
        processService.updateCheckGoods(paramMap, cgsFile);
        rm.setMessage("자산 필요 공정 내용 업데이트 완료");
        return rm;
    }
}

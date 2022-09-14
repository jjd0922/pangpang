package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stock/")
public class StockController {

    /**적재관리 메인페이지*/
    @GetMapping("load")
    public ModelAndView loading() {
        ModelAndView mav = new ModelAndView("/stock/load");
        return mav;
    }

    /**적재관리 > 재고인수대기 버튼 > 자산처리 팝업 페이지*/
    @GetMapping("load/wait")
    public ModelAndView stockWaiting() {
        ModelAndView mav = new ModelAndView("/stock/load_wait");
        return mav;
    }

    /**적재관리 > 재고인수(적재) 버튼 > 재고적재 페이지*/
    @GetMapping("load/take")
    public ModelAndView stockTaking() {
        ModelAndView mav = new ModelAndView("/stock/load_take");
        return mav;
    }

    /**적재관리 > 바코드_재고인수(적재) 버튼 > 재고적재 페이지*/
    @GetMapping("load/take/barcode")
    public ModelAndView stockTakingByBarcode() {
        ModelAndView mav = new ModelAndView("/stock/load_take");
        mav.addObject("barcode", " ");
        return mav;
    }

    /**재고현황 메인페이지 > 로케이션별 재고현황 조회*/
    @GetMapping("")
    public ModelAndView stock() {
        ModelAndView mav = new ModelAndView("/stock/stock");
        return mav;
    }
    
    /**재고현황 > 해당 로케이션의 재고자산 상세*/
    @GetMapping("goods")
    public ModelAndView goodsInLocation() {
        ModelAndView mav = new ModelAndView("/stock/goods");
        return mav;
    }
    
    /**창고이동관리 메인페이지*/
    @GetMapping("move")
    public ModelAndView stockMove() {
        ModelAndView mav = new ModelAndView("/stock/move");
        return mav;
    }

    /**창고이동관리 > 창고이동등록 페이지*/
    @GetMapping("move/pop")
    public ModelAndView stockMovePop() {
        ModelAndView mav = new ModelAndView("/stock/move_pop");
        return mav;
    }

    /**창고이동관리 > 창고이동 조회/수정 페이지*/
    @GetMapping("move/pop/{idx}") //pathvariable 부분 임시, 오브젝트도 임시.(추후 수정~)
    public ModelAndView stockMovePopDetail(@PathVariable Integer idx) {
        ModelAndView mav = new ModelAndView("/stock/move_pop");
        mav.addObject("tempObject", " ");
        return mav;
    }

    /**재고관리 피킹관리 페이지*/
    @GetMapping("picking")
    public ModelAndView picking() {
        ModelAndView mav = new ModelAndView("/stock/picking");
        return mav;
    }
}

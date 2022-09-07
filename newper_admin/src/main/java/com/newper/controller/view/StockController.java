package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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


}

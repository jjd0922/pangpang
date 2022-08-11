package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/process/")
@RequiredArgsConstructor
public class ProcessController {


    /**공정보드 공정관리 페이지**/
    @GetMapping(value = "")
    public ModelAndView process() {
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
    public ModelAndView needPopup() {
        ModelAndView mav = new ModelAndView("process/needPop");

        return mav;
    }

    /**수리요청 팝업**/
    @GetMapping(value = "fixPop")
    public ModelAndView fixPopup() {
        ModelAndView mav = new ModelAndView("process/fixPop");

        return mav;
    }

    /**도색요청 팝업**/
    @GetMapping(value = "paintPop")
    public ModelAndView paintPopup() {
        ModelAndView mav = new ModelAndView("process/paintPop");

        return mav;
    }

    /**재검수요청 팝업**/
    @GetMapping(value = "recheckPop")
    public ModelAndView recheckPopup() {
        ModelAndView mav = new ModelAndView("process/recheckPop");

        return mav;
    }

    /**망실 팝업**/
    @GetMapping(value = "lostPop")
    public ModelAndView lostPopup() {
        ModelAndView mav = new ModelAndView("process/lostPop");

        return mav;
    }
    /**재고인계 팝업**/
    @GetMapping(value = "productHandoverPop")
    public ModelAndView productHandoverPopup() {
        ModelAndView mav = new ModelAndView("process/productHandoverPop");

        return mav;
    }

    /**매입처반품 페이지**/
    @GetMapping(value = "purchasingReturn")
    public ModelAndView purchasingReturn() {
        ModelAndView mav = new ModelAndView("process/purchasingReturn");

        return mav;
    }


    /**매입처반품 반품관리팝업**/
    @GetMapping(value = "returnManagePop")
    public ModelAndView returnManagePop() {
        ModelAndView mav = new ModelAndView("process/returnManagePop");

        return mav;
    }
}

package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/purchaseSettlement/")
public class PurchaseSettlementController {


    /**상품매입정산(매입처) 페이지*/
    @GetMapping("productCal")
    public ModelAndView productCal() {
        ModelAndView mav = new ModelAndView("/purchaseSettlement/productCal");
        return mav;
    }


    /**상품매입정산(매입처) 정산처리팝업*/
    @GetMapping("calPop")
    public ModelAndView calPop() {
        ModelAndView mav = new ModelAndView("/purchaseSettlement/calPop");
        return mav;
    }

    /**상품매입정산(매입처) 마감처리팝업*/
    @GetMapping("deadlinePop")
    public ModelAndView deadlinePop() {
        ModelAndView mav = new ModelAndView("/purchaseSettlement/deadlinePop");
        return mav;
    }

}

package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/salesSettlement/")
public class SalesSettlementController {


    /**판매매출관리 (자사몰) 페이지*/
    @GetMapping("own")
    public ModelAndView own() {
        ModelAndView mav = new ModelAndView("/salesSettlement/own");
        return mav;
    }


    /**판매매출관리 (자사몰) 페이지 정산처리팝업*/
    @GetMapping("ownPop")
    public ModelAndView ownPop() {
        ModelAndView mav = new ModelAndView("/salesSettlement/ownPop");
        return mav;
    }

    /**BAN정산관리 페이지*/
    @GetMapping("ban")
    public ModelAndView ban() {
        ModelAndView mav = new ModelAndView("/salesSettlement/ban");
        return mav;
    }


    /**BAN정산관리 페이지 정산처리팝업*/
    @GetMapping("banPop")
    public ModelAndView banPop() {
        ModelAndView mav = new ModelAndView("/salesSettlement/banPop");
        return mav;
    }

}

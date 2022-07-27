package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/company/")
@Controller
@RequiredArgsConstructor
public class CompanyController {

    /** 거래처 관리 페이지*/
    @GetMapping(value = "")
    public ModelAndView company(){
        ModelAndView mav = new ModelAndView("company/company");

        return mav;
    }

    /** 거래처 신규등록 팝업 */
    @GetMapping(value = "regist")
    public ModelAndView regist(){
        ModelAndView mav = new ModelAndView("company/regist");

        return mav;
    }

    /** 거래처 계약관리 조회 페이지 **/
    @GetMapping(value = "contract")
    public ModelAndView contract(){
        ModelAndView mav = new ModelAndView("company/contract");

        return mav;
    }
}

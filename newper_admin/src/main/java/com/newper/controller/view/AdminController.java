package com.newper.controller.view;

import com.newper.constant.TfType;
import com.newper.entity.Auth;
import com.newper.entity.ConsultationResult;
import com.newper.entity.TemplateForm;
import com.newper.entity.User;
import com.newper.repository.ConsultationResultRepo;
import com.newper.repository.TemplateFormRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminController {
    private final TemplateFormRepo templateFormRepo;

    private final ConsultationResultRepo consultationResultRepo;

    /**뮨자 템플릿 페이지*/
    @GetMapping(value = "smsTemplate")
    public ModelAndView smsTemplate(){
        ModelAndView mav = new ModelAndView("admin/smsTemplate");

        return mav;
    }
    /**카카오톡 템플릿 페이지*/
    @GetMapping(value = "kakaoTemplate")
    public ModelAndView kakaoTemplate(){
        ModelAndView mav = new ModelAndView("admin/kakaoTemplate");

        return mav;
    }



    /**문자 템플릿 팝업페이지*/
    @GetMapping(value = "smsPop")
    public ModelAndView smsPop(){
        ModelAndView mav = new ModelAndView("admin/smsPop");
     /*   mav.addObject("M",TfType.M);*/
        return mav;
    }

    /**
     * 문자템플릿 상세조회 페이지
     */
    @GetMapping("smsPop/{tfIdx}")
    public ModelAndView smsDetail(@PathVariable Integer tfIdx) {
        ModelAndView mav = new ModelAndView("admin/smsPop");
        TemplateForm templateForm = templateFormRepo.findTemplateFormBytfIdx(tfIdx);

   /*     mav.addObject("tfType", TfType.M);*/
        mav.addObject("templateForm", templateForm);
        return mav;
    }

    /**카카오톡 템플릿 팝업페이지*/
    @GetMapping(value = "kakaoPop")
    public ModelAndView kakaoPop(){
        ModelAndView mav = new ModelAndView("admin/kakaoPop");

        return mav;
    }
    /**
     * 카카오템플릿 상세조회 페이지
     */
    @GetMapping("kakaoPop/{tfIdx}")
    public ModelAndView kakaoDetail(@PathVariable Integer tfIdx) {
        ModelAndView mav = new ModelAndView("admin/smsPop");
        TemplateForm templateForm = templateFormRepo.findTemplateFormBytfIdx(tfIdx);

        mav.addObject("templateForm", templateForm);
        return mav;
    }

    /**상담결과 페이지*/
    @GetMapping(value = "counselResult")
    public ModelAndView counselResult(){
        ModelAndView mav = new ModelAndView("admin/counselResult");

        return mav;
    }


    /**상담결과 팝업페이지*/
    @GetMapping(value = "counselPop")
    public ModelAndView counselPop(){
        ModelAndView mav = new ModelAndView("admin/counselPop");

        return mav;
    }

    /**
     * 카카오템플릿 상세조회 페이지
     */
    @GetMapping("counselPop/{crIdx}")
    public ModelAndView counselDetail(@PathVariable Integer crIdx) {
        ModelAndView mav = new ModelAndView("admin/counselPop");

        ConsultationResult consultationResult = consultationResultRepo.findConsultationResultBycrIdx(crIdx);

        mav.addObject("consultationResult", consultationResult);
        return mav;
    }
}

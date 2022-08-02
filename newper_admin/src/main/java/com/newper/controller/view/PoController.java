package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.exception.MsgException;
import com.newper.service.PoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping(value = "/po/")
@Controller
@RequiredArgsConstructor
public class PoController {
    private final PoService poService;

    /** 견적서 관리 페이지 **/
    @GetMapping(value = "estimate")
    public ModelAndView estimate(){
        ModelAndView mav = new ModelAndView("po/estimate");
        return mav;
    }

    /** 견적서 관리 페이지 **/
    @GetMapping(value = "estimatePop")
    public ModelAndView estimateNew(){
        ModelAndView mav = new ModelAndView("po/estimatePop");
        return mav;
    }

    /** 견적서 생성 **/
    @PostMapping(value = "estimatePop")
    public ModelAndView estimateNewPost(ParamMap paramMap, MultipartFile peEstimateFile){
        ModelAndView mav = new ModelAndView("po/estimatePop");
        if (peEstimateFile.getSize() == 0) {
            throw new MsgException ("파일을 첨부해 주세요");
        }
        poService.saveEstimate(paramMap, peEstimateFile);
        return mav;
    }

    /** 견적서 관리 페이지 **/
    @GetMapping(value = "estimatePop/{peIdx}")
    public ModelAndView estimateDetail(@PathVariable long peIdx){
        ModelAndView mav = new ModelAndView("po/estimatePop");
        return mav;
    }
}

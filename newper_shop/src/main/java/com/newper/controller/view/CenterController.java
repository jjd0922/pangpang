package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/center/")
public class CenterController {



    /** 공지사항 상세*/
    @GetMapping(value = "notice/{ntIdx}")
    public ModelAndView faq(@PathVariable Integer ntIdx){
        ModelAndView mav = new ModelAndView("custCenter/ntIdx");

        return mav;
    }


}

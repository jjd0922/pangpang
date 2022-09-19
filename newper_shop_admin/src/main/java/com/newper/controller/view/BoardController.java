package com.newper.controller.view;

import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/board/")
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ShopRepo shopRepo;

    /** 공지사항*/
    @GetMapping("notice")
    public ModelAndView notice(){
        ModelAndView mav = new ModelAndView("board/notice");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }
    /** 공지사항 신규,상세*/
    @GetMapping(value = {"notice/new", "notice/{ntIdx}"})
    public ModelAndView ntIdx(@PathVariable(required = false) Long ntIdx){
        ModelAndView mav = new ModelAndView("board/ntIdx");

        return mav;
    }

    /** FAQ*/
    @GetMapping("faq")
    public ModelAndView faq(){
        ModelAndView mav = new ModelAndView("board/faq");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }
    /** 1:1 문의*/
    @GetMapping("qna")
    public ModelAndView qna(){
        ModelAndView mav = new ModelAndView("board/qna");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }
    /** 보기쉬운 FAQ*/
    @GetMapping("faq/easy")
    public ModelAndView faqEasy(){
        ModelAndView mav = new ModelAndView("board/faqEasy");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }


}

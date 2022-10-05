package com.newper.controller.view;

import com.newper.constant.Channel;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.repository.EventGroupRepo;
import com.newper.repository.NoticeRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@RequestMapping(value = "/board/")
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ShopRepo shopRepo;
    private final NoticeRepo noticeRepo;
    private final EventGroupRepo eventGroupRepo;

    /** 이벤트 관리*/
    @GetMapping("event")
    public ModelAndView event(){
        ModelAndView mav = new ModelAndView("board/event");
        mav.addObject("shopList", shopRepo.findAll());
        return mav;
    }
    /** 공지사항*/
    @GetMapping("notice")
    public ModelAndView notice(){
        ModelAndView mav = new ModelAndView("board/notice");

        mav.addObject("shopList", shopRepo.findAll());
        return mav;
    }
    /** FAQ*/
    @GetMapping("faq")
    public ModelAndView faq(){
        ModelAndView mav = new ModelAndView("board/faq");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }

    /** 홍보센터 */
    @GetMapping("center")
    public ModelAndView center(){
        ModelAndView mav = new ModelAndView("board/center");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }
    /** 홍보센터 */
    @GetMapping("news")
    public ModelAndView news(){
        ModelAndView mav = new ModelAndView("board/news");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }
    /** 지점정보 */
    @GetMapping("store")
    public ModelAndView store(){
        ModelAndView mav = new ModelAndView("board/store");

        mav.addObject("shopList", shopRepo.findAll());

        return mav;
    }

    /** 공지사항 신규,상세*/
    @GetMapping(value = {"notice/new", "notice/{ntIdx}"})
    public ModelAndView ntIdx(@PathVariable(required = false) Long ntIdx){
        ModelAndView mav = new ModelAndView("board/notice_ntIdx");

        if(ntIdx != null){
            mav.addObject("notice", noticeRepo.findShopByNtIdx(ntIdx));
        }

        return mav;
    }
    /** 이벤트/기획전 신규,상세*/
    @GetMapping(value = {"event/new/{shopIdx}", "event/{egIdx}/{shopIdx}"})
    public ModelAndView egIdx(@PathVariable(required = false) Long egIdx, @PathVariable Integer shopIdx){
        ModelAndView mav = new ModelAndView("board/event_egIdx");

        if(egIdx != null){
//            mav.addObject("event", eventGroupRepo.findShopByEgIdx(egIdx));
        }
        Shop shop = shopRepo.findById(shopIdx).orElseThrow(()-> new MsgException("존재하지 않는 분양몰입니다."));
        mav.addObject("shop", shop);
        return mav;
    }




}

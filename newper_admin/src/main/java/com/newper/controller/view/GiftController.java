package com.newper.controller.view;

import com.newper.exception.MsgException;
import com.newper.repository.GiftGroupRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/gift/")
@Controller
@RequiredArgsConstructor
public class GiftController {

    private final GiftGroupRepo giftGroupRepo;

    /**상품권관리 페이지*/
    @GetMapping("")
    public ModelAndView gift() {
        ModelAndView mav = new ModelAndView("gift/gift");

        return mav;
    }

    /**상품권 등록 페이지*/
    @GetMapping("giftPop")
    public ModelAndView newGift() {
        ModelAndView mav = new ModelAndView("gift/giftPop");

        return mav;
    }



    @GetMapping("giftPop/{giftgIdx}")
    public ModelAndView giftDetail(@PathVariable Long giftgIdx) {
        ModelAndView mav = new ModelAndView("gift/giftPop");

        mav.addObject("gift", giftGroupRepo.findById(giftgIdx).orElseThrow(()->new MsgException("존재하지 않는 상품권입니다.")));
        return mav;
    }

}

package com.newper.controller.view;

import com.newper.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coupon/")
public class CouponController {

    private final CategoryMapper categoryMapper;

    /**쿠폰생성관리 페이지*/
    @GetMapping("")
    public ModelAndView coupon() {
        ModelAndView mav = new ModelAndView("/coupon/coupon");

        return mav;
    }

    /**쿠폰생선관리 신규등록 페이지*/
    @GetMapping(value="detail")
    public ModelAndView newCouponPop() {
        ModelAndView mav = new ModelAndView("/coupon/detail");
        mav.addObject("category", categoryMapper.selectCategoryListByCateDepth(2));
        return mav;
    }

}

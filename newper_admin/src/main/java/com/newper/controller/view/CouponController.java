package com.newper.controller.view;

import com.newper.mapper.CategoryMapper;
import com.newper.repository.CouponGroupRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coupon/")
public class CouponController {

    private final CategoryMapper categoryMapper;
    private final CouponGroupRepo couponGroupRepo;

    /**쿠폰생성관리 페이지*/
    @GetMapping("")
    public ModelAndView coupon() {
        ModelAndView mav = new ModelAndView("/coupon/coupon");

        return mav;
    }

    /**쿠폰생선관리 신규등록 페이지*/
    @GetMapping("detail")
    public ModelAndView newCouponPop() {
        ModelAndView mav = new ModelAndView("/coupon/detail");
        mav.addObject("category", categoryMapper.selectCategoryListByCateDepth(2));
        return mav;
    }

    @GetMapping("detail/{cpgIdx}")
    public ModelAndView couponDetailPop(@PathVariable Long cpgIdx) {
        ModelAndView mav = new ModelAndView("/coupon/detail");
        mav.addObject("category", categoryMapper.selectCategoryListByCateDepth(2));
        mav.addObject("couponGroup", couponGroupRepo.findBycpgIdx(cpgIdx));
        return mav;
    }

}

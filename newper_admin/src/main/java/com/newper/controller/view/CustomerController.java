package com.newper.controller.view;

import com.newper.entity.Customer;
import com.newper.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/customer/")
public class CustomerController {

    private final CustomerRepo customerRepo;

    @GetMapping(value = "")
    public ModelAndView customer() {
        ModelAndView mav = new ModelAndView("/customer/customer");

        return mav;
    }

    @GetMapping(value = "detail/{cuIdx}")
    public ModelAndView customerDetail(@PathVariable Long cuIdx) {
        ModelAndView mav = new ModelAndView("/customer/detail");

        mav.addObject("customer", customerRepo.findBycuIdx(cuIdx));
        return mav;
    }

    @GetMapping(value = "detail/qna")
    public ModelAndView popQnaForAnswer() {
        ModelAndView mav = new ModelAndView("/customer/detail_qna");

        return mav;
    }

    @GetMapping(value = "pop/coupon")
    public ModelAndView couponPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_coupon");

        return mav;
    }

    @GetMapping(value = "pop/point")
    public ModelAndView pointPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_point");

        return mav;
    }

    @GetMapping(value = "pop/sms")
    public ModelAndView smsPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_sms");

        return mav;
    }

    @GetMapping(value = "pop/kakao")
    public ModelAndView kakaoPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_kakao");

        return mav;
    }

    @GetMapping(value = "pop/appPush")
    public ModelAndView appPushPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_appPush");

        return mav;
    }

}

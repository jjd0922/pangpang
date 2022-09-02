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

    /**회원관리 페이지*/
    @GetMapping(value = "")
    public ModelAndView customer() {
        ModelAndView mav = new ModelAndView("/customer/customer");

        return mav;
    }
    /**발송내역 페이지*/
    @GetMapping(value = "sendingHistory")
    public ModelAndView sendingHistory(){
        ModelAndView mav = new ModelAndView("customer/sendingHistory");

        return mav;
    }
    
    /**회원상세조회 ECU01_002*/
    @GetMapping(value = "detail/{cuIdx}")
    public ModelAndView customerDetail(@PathVariable Long cuIdx) {
        ModelAndView mav = new ModelAndView("/customer/detail");

        mav.addObject("customer", customerRepo.findBycuIdx(cuIdx));
        return mav;
    }

    /**회원상세조회 > 1:1 문의 답변*/
    @GetMapping(value = "detail/qna")
    public ModelAndView popQnaForAnswer() {
        ModelAndView mav = new ModelAndView("/customer/detail_qna");

        return mav;
    }

    /**회원관리 > 쿠폰지급*/
    @GetMapping(value = "pop/coupon")
    public ModelAndView couponPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_coupon");

        return mav;
    }

    /**회원관리 > 적립금지급*/
    @GetMapping(value = "pop/point")
    public ModelAndView pointPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_point");

        return mav;
    }

    /**회원관리 > 상품권지급*/
    @GetMapping(value = "pop/gift")
    public ModelAndView giftPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_gift");

        return mav;
    }

    /**회원관리 > SMS발송*/
    @GetMapping(value = "pop/sms")
    public ModelAndView smsPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_sms");

        return mav;
    }

    /**회원관리 > 카카오톡발송*/
    @GetMapping(value = "pop/kakao")
    public ModelAndView kakaoPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_kakao");

        return mav;
    }

    /**회원관리 > APP PUSH*/
    @GetMapping(value = "pop/appPush")
    public ModelAndView appPushPopup() {
        ModelAndView mav = new ModelAndView("/customer/pop_appPush");

        return mav;
    }

    /**고객별 쿠폰사용내역*/
    @GetMapping(value = "history/coupon")
    public ModelAndView couponHistory() {
        ModelAndView mav = new ModelAndView("/customer/history_coupon");

        return mav;
    }

    /**고객별 적립금(마일리지)사용내역*/
    @GetMapping(value = "history/mileage")
    public ModelAndView mileageHistory() {
        ModelAndView mav = new ModelAndView("/customer/history_mileage");

        return mav;
    }

    /**고객별 적립금(예치금)사용내역*/
    @GetMapping(value = "history/point")
    public ModelAndView pointHistory() {
        ModelAndView mav = new ModelAndView("/customer/history_point");

        return mav;
    }


}

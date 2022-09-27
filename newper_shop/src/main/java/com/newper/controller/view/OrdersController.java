package com.newper.controller.view;

import com.newper.constant.PayState;
import com.newper.entity.Payment;
import com.newper.exception.MsgException;
import com.newper.iamport.IamportApi;
import com.newper.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/orders/")
@Controller
@RequiredArgsConstructor
public class OrdersController {

    private final PaymentService paymentService;

    /** 주문 결제 페이지*/
    @GetMapping("")
    public ModelAndView orders(){
        ModelAndView mav = new ModelAndView("orders/orders");


        return mav;
    }
    /** 결제 결과*/
    @GetMapping("result/ph{idx}")
    public ModelAndView resultPh(@PathVariable(value = "idx") long ph_idx){
        ModelAndView mav = new ModelAndView();
        //[success=true, imp_uid=imp_803037736674, pay_method=card, merchant_uid=ph27, name=test 외 1건, paid_amount=100, currency=KRW, pg_provider=kcp, pg_type=payment, pg_tid=22895235578509, apply_num=30046339, buyer_name=주문자명, buyer_email=, buyer_tel=, buyer_addr=, buyer_postcode=, custom_data=null, status=paid, paid_at=1663830096, receipt_url=https://admin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd=card_bill&tno=22895235578509&order_no=imp_803037736674&trade_mony=100, card_name=KB국민카드, bank_name=null, card_quota=0, card_number=5570420000007221]


        String response_str;
        try{
            response_str = new IamportApi().checkPay("ph"+ph_idx);
        }catch (Exception e){
            throw new MsgException("결제 조회 중 에러 발생", e);
        }

        Payment payment = paymentService.savePaymentResult(ph_idx, response_str);

        if (payment.getPayState() == PayState.SUCCESS) {
            mav.setViewName("redirect:/orders/result"+payment.getOrders().getOIdx());
        }else{
            throw new MsgException("결제 결과 확인 중입니다.");
        }

        return mav;
    }

    /** 주문 상세 페이지*/
    @GetMapping("{oCode}")
    public ModelAndView oCode(@PathVariable String oCode){
        ModelAndView mav = new ModelAndView("orders/oCode");

        System.out.println("o_code");
        System.out.println(oCode);


        return mav;
    }
}

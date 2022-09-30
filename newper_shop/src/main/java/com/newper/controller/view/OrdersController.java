package com.newper.controller.view;

import com.newper.component.ShopSession;
import com.newper.constant.PayState;
import com.newper.dto.OrdersSpoDTO;
import com.newper.dto.ParamMap;
import com.newper.entity.Customer;
import com.newper.entity.Payment;
import com.newper.entity.ShopProduct;
import com.newper.exception.MsgException;
import com.newper.iamport.IamportApi;
import com.newper.mapper.IamportMapper;
import com.newper.repository.CustomerRepo;
import com.newper.repository.ShopProductOptionRepo;
import com.newper.service.PaymentService;
import com.newper.service.ShopProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping(value = "/orders/")
@Controller
@RequiredArgsConstructor
public class OrdersController {

    @Autowired
    private ShopSession shopSession;

    private final PaymentService paymentService;
    private final IamportMapper iamportMapper;
    private final ShopProductService shopProductService;
    private final CustomerRepo customerRepo;

    /** 주문 결제 페이지*/
    @PostMapping("")
    public ModelAndView orders(ParamMap paramMap){
        ModelAndView mav = new ModelAndView("orders/orders");

        Map<ShopProduct, List<OrdersSpoDTO>> shopProductListMap = shopProductService.selectOrdersInfo(paramMap);
        mav.addObject("spMap", shopProductListMap);

        int totalCnt = 0;
        int totalPrice = 0;
        for (List<OrdersSpoDTO> dtoList : shopProductListMap.values()) {
            for (OrdersSpoDTO dto : dtoList) {
                totalCnt += dto.getCnt();
                totalPrice += dto.getPrice() * dto.getCnt();
            }
        }
        mav.addObject("totalCnt", totalCnt);
        mav.addObject("totalPrice", totalPrice);

        if (shopSession.getIdx() != null) {
            Customer customer = customerRepo.findById(shopSession.getIdx()).get();
            mav.addObject("customer", customer);
        }


        //pg 정보
        mav.addObject("payNormalList", iamportMapper.selectIamportMethodList());
        mav.addObject("payEasyList", iamportMapper.selectIamportPgList());


        return mav;
    }
    /** iframe에서 결제 결과 확인 후 redirect*/
    @GetMapping("result/ph{idx}")
    public ModelAndView resultPh(@PathVariable(value = "idx") long ph_idx){
        ModelAndView mav = new ModelAndView("orders/result_ph_idx");

        String response_str;
        try{
            response_str = new IamportApi().checkPay("ph"+ph_idx);
        }catch (Exception e){
            throw new MsgException("결제 조회 중 에러 발생", e);
        }

        Payment payment = paymentService.savePaymentResult(ph_idx, response_str);

        if (payment.getPayState() == PayState.SUCCESS) {
            mav.addObject("result","success");
            mav.addObject("href","/orders/" + payment.getOrders().getOCode());
        }else if (payment.getPayState() == PayState.FAIL) {
            mav.addObject("result","fail");
        }else{
            throw new MsgException("결제 결과 확인 중입니다.");
        }

        return mav;
    }

    /** 결제 완료 후 보는 주문 상세 페이지*/
    @GetMapping("{oCode}")
    public ModelAndView oCode(@PathVariable String oCode){
        ModelAndView mav = new ModelAndView("orders/oCode");

        System.out.println("o_code");
        System.out.println(oCode);


        return mav;
    }
    /** iamport 결제 js 페이지*/
    @GetMapping("iamport")
    public ModelAndView iamport(){
        ModelAndView mav = new ModelAndView("orders/iamport");
        return mav;
    }
}

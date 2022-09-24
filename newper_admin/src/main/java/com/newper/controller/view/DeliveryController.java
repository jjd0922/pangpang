package com.newper.controller.view;

import com.newper.constant.OLocation;
import com.newper.mapper.OrdersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RequestMapping(value = "/delivery/")
@RequiredArgsConstructor
@RestController
public class DeliveryController {

    private final OrdersMapper ordersMapper;

    /**
     * 주문관리 배송관리 페이지
     */
    @GetMapping("delivery")
    public ModelAndView delivery() {
        ModelAndView mav = new ModelAndView("delivery/delivery");

        return mav;
    }

    /**
     * 주문관리 배송관리 송장등록 팝업 페이지
     */
    @GetMapping("invoiceUploadPop")
    public ModelAndView invoiceUploadPop() {
        ModelAndView mav = new ModelAndView("delivery/invoiceUploadPop");
        return mav;
    }

    /**
     * 주문관리 배송관리 송장통합 팝업 페이지
     */
    @GetMapping("invoiceIntegratedPop")
    public ModelAndView invoiceIntegratedPop() {
        ModelAndView mav = new ModelAndView("delivery/invoiceIntegratedPop");
        return mav;
    }

    /**
     * 주문관리 배송관리 출고등록 팝업 페이지
     */
    @GetMapping("releasePop")
    public ModelAndView releasePop() {
        ModelAndView mav = new ModelAndView("delivery/releasePop");
        return mav;
    }

    /**
     * 주문관리 배송관리 주문코드 상세 팝업 페이지
     */
    @GetMapping("deliveryDetailPop")
    public ModelAndView deliveryDetailPop() {
        ModelAndView mav = new ModelAndView("delivery/deliveryDetailPop");
        return mav;
    }

    /**
     * 주문관리 설치관리 페이지
     */
    @GetMapping("installation")
    public ModelAndView installation() {
        ModelAndView mav = new ModelAndView("delivery/installation");

        return mav;
    }

    /**
     * 주문관리 배송상품반품관리 페이지
     */
    @GetMapping("deliveryReturn")
    public ModelAndView deliveryReturn() {
        ModelAndView mav = new ModelAndView("delivery/deliveryReturn");

        return mav;
    }

    /**
     * 주문관리 설치상품반품관리 페이지
     */
    @GetMapping("installationReturn")
    public ModelAndView installationReturn() {
        ModelAndView mav = new ModelAndView("delivery/installationReturn");

        return mav;
    }

    @GetMapping("integratedDetail")
    public ModelAndView integratedDetail() {
        ModelAndView mav = new ModelAndView("delivery/integratedDetail");

        return mav;
    }

    @GetMapping("integratedDetail/{oIdx}")
    public ModelAndView detail(@PathVariable int oIdx) {
        ModelAndView mav = new ModelAndView("delivery/integratedDetail");

        Map<String, Object> map = ordersMapper.selectOrderDetail(oIdx);
        String o_location = OLocation.valueOf(map.get("O_LOCATION") + "").getOption();
        map.put("O_LOCATION", o_location);
        mav.addObject("order", map);

        return mav;
    }
}
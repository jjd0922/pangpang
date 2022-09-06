package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.entity.Auth;
import com.newper.entity.CalculateSetting;
import com.newper.entity.User;
import com.newper.repository.CalculateSettingRepo;
import com.newper.service.PurchaseCalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/purchaseCal/")
public class PurchaseCalController {

    private final CalculateSettingRepo calculateSettingRepo;

    private  final PurchaseCalService purchaseCalService;


    /**벤더 정산 설정 페이지*/
    @GetMapping(value = "vendorSetting")
    public ModelAndView vendorSetting(){
        ModelAndView mav = new ModelAndView("purchaseCal/vendorSetting");

        return mav;
    }

    /**벤더 정산 팝업*/
    @GetMapping(value = "vendorPop")
    public ModelAndView vendorPop(){
        ModelAndView mav = new ModelAndView("purchaseCal/vendorPop");

        return mav;
    }

    /**
     * 벤더정산 팝업 상세조회 페이지
     */
    @GetMapping("vendorPop/{csIdx}")
    public ModelAndView vendorPop(@PathVariable Integer csIdx) {
        ModelAndView mav = new ModelAndView("purchaseCal/vendorPop");


        CalculateSetting calculateSetting =  calculateSettingRepo.findCalculateSettingBycsIdx(csIdx);


        mav.addObject("calculateSetting", calculateSetting);
        return mav;
    }

    /**벤더정산 팝업 수정처리*/
    @PostMapping("vendorPop/{csIdx}")
    public ModelAndView updateVendor(@PathVariable Integer csIdx, ParamMap paramMap){
        ModelAndView mav = new ModelAndView("main/alertMove");
        System.out.println(paramMap.getMap());

        purchaseCalService.updateVendor(paramMap,csIdx);
        mav.addObject("msg", "수정 완료");
        mav.addObject("loc", "purchaseCal/vendorPop" + csIdx);
        return mav;



    }
}

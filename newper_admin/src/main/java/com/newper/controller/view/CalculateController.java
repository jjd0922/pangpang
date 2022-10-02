package com.newper.controller.view;

import com.newper.entity.CalculateGroup;
import com.newper.repository.CalculateAdjustRepo;
import com.newper.repository.CalculateGroupRepo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping(value = "/calculate/")
@Controller
@RequiredArgsConstructor
public class CalculateController {

    private final CalculateGroupRepo calculateGroupRepo;
    private final CalculateAdjustRepo calculateAdjustRepo;

    /** 팬매매출관리(자사몰) */
    @GetMapping(value = "salesOwnMall")
    public ModelAndView salesOwnMall (){
        ModelAndView mav = new ModelAndView("calculate/salesOwnMall");
        return mav;
    }

    /** BAN(SCM)정산관리 */
    @GetMapping(value = "salesBan")
    public ModelAndView salesBan (){
        ModelAndView mav = new ModelAndView("calculate/salesBan");
        return mav;
    }

    /** 상품매입정산 리스트 */
    @GetMapping(value = "productPurchase")
    public ModelAndView productPurchase (){
        ModelAndView mav = new ModelAndView("calculate/productPurchase");
        return mav;
    }

    /** 상품매입정산 상세 */
    @GetMapping(value = "productPurchasePop/{ccgIdx}")
    public ModelAndView productPurchasePop (@PathVariable int ccgIdx){
        ModelAndView mav = new ModelAndView("calculate/productPurchasePop");
        CalculateGroup calculateGroup = calculateGroupRepo.findByCcgIdx(ccgIdx);
        mav.addObject("ccg", calculateGroup);
        mav.addObject("adjust", calculateAdjustRepo.findByCalculateGroup(calculateGroup));
        return mav;
    }

    /** 입점사매입정산 리스트 */
    @GetMapping(value = "storePurchase")
    public ModelAndView storePurchase (){
        ModelAndView mav = new ModelAndView("calculate/storePurchase");
        return mav;
    }

    /** 입점사매입정산 상세 */
    @GetMapping(value = "storePurchasePop/{ccgIdx}")
    public ModelAndView storePurchasePop (@PathVariable int ccgIdx){
        ModelAndView mav = new ModelAndView("calculate/storePurchasePop");
        CalculateGroup calculateGroup = calculateGroupRepo.findByCcgIdx(ccgIdx);
        mav.addObject("ccg", calculateGroup);
        mav.addObject("adjust", calculateAdjustRepo.findByCalculateGroup(calculateGroup));
        return mav;
    }

    /** PG정산관리 리스트 */
    @GetMapping(value = "pg")
    public ModelAndView pg (){
        ModelAndView mav = new ModelAndView("calculate/pg");
        return mav;
    }

    /** PG정산관리 상세 */
    @GetMapping(value = "pgPop/{ccgIdx}")
    public ModelAndView pgPop (@PathVariable int ccgIdx){
        ModelAndView mav = new ModelAndView("calculate/pgPop");
        CalculateGroup calculateGroup = calculateGroupRepo.findByCcgIdx(ccgIdx);
        mav.addObject("ccg", calculateGroup);
        mav.addObject("adjust", calculateAdjustRepo.findByCalculateGroup(calculateGroup));
        return mav;
    }

    /** 외주공정비 리스트 */
    @GetMapping(value = "process")
    public ModelAndView process (){
        ModelAndView mav = new ModelAndView("calculate/process");
        return mav;
    }

    /** 외주공정비 상세 */
    @GetMapping(value = "processPop/{ccgIdx}")
    public ModelAndView processPop (@PathVariable int ccgIdx){
        ModelAndView mav = new ModelAndView("calculate/processPop");
        CalculateGroup calculateGroup = calculateGroupRepo.findByCcgIdx(ccgIdx);
        mav.addObject("ccg", calculateGroup);
        mav.addObject("adjust", calculateAdjustRepo.findByCalculateGroup(calculateGroup));
        return mav;
    }

    /** 배송비정산 리스트 */
    @GetMapping(value = "delivery")
    public ModelAndView delivery (){
        ModelAndView mav = new ModelAndView("calculate/delivery");
        return mav;
    }

    /** 배송비정산 상세 */
    @GetMapping(value = "deliveryPop/{ccgIdx}")
    public ModelAndView deliveryPop (@PathVariable int ccgIdx){
        ModelAndView mav = new ModelAndView("calculate/deliveryPop");
        CalculateGroup calculateGroup = calculateGroupRepo.findByCcgIdx(ccgIdx);
        mav.addObject("ccg", calculateGroup);
        mav.addObject("adjust", calculateAdjustRepo.findByCalculateGroup(calculateGroup));
        return mav;
    }

    /** 설치비정산 리스트 */
    @GetMapping(value = "install")
    public ModelAndView install (){
        ModelAndView mav = new ModelAndView("calculate/install");
        return mav;
    }

    /** 외주공정비 상세 */
    @GetMapping(value = "installPop/{ccgIdx}")
    public ModelAndView installPop (@PathVariable int ccgIdx){
        ModelAndView mav = new ModelAndView("calculate/installPop");
        CalculateGroup calculateGroup = calculateGroupRepo.findByCcgIdx(ccgIdx);
        mav.addObject("ccg", calculateGroup);
        mav.addObject("adjust", calculateAdjustRepo.findByCalculateGroup(calculateGroup));
        return mav;
    }
}

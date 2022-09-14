package com.newper.controller.view;

import com.newper.entity.PoProduct;
import com.newper.mapper.ChecksMapper;
import com.newper.mapper.PoMapper;
import com.newper.repository.CheckGroupRepo;
import com.newper.repository.InGroupRepo;
import com.newper.repository.PoProductRepo;
import com.newper.repository.PoRepo;
import com.newper.service.InService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/** 입고등록 controller*/
@RequestMapping(value = "/in/")
@RequiredArgsConstructor
@Controller
public class InController {
    private final PoRepo poRepo;
    private final InGroupRepo inGroupRepo;
    private final InService inService;
    private final CheckGroupRepo checkGroupRepo;
    private final ChecksMapper checksMapper;
    private final PoMapper poMapper;




    /** 입고등록*/
    @GetMapping("")
    public ModelAndView in(){
        ModelAndView mav = new ModelAndView("in/in");

        return mav;
    }
    @GetMapping("po/{poIdx}")
    public ModelAndView inIdx(@PathVariable int poIdx){
        ModelAndView mav = new ModelAndView("in/po_poIdx");
        inService.insertInGroup(poIdx);
        mav.addObject("ig", inGroupRepo.findInGroupByPoPoIdx(poIdx));
        mav.addObject("po", poRepo.findPoByPoIdx(poIdx));

        return mav;
    }

    /** 입고검수 페이지*/
    @GetMapping("incheck")
    public ModelAndView incheck(){
        ModelAndView mav = new ModelAndView("in/incheck");

        return mav;
    }

    /** 입고검수 입고검수코드 팝업*/
    @GetMapping("incheckManagePop/{cgIdx}")
    public ModelAndView inCheckManagePop(@PathVariable int cgIdx){
        ModelAndView mav = new ModelAndView("in/incheckManagePop");
        mav.addObject("check", checkGroupRepo.findCheckGroupByCgIdx(cgIdx));
        mav.addObject("totalPrice", checksMapper.selectCheckGroupExpectedCostTotal(cgIdx));
        return mav;
    }
    /** 영업검수 페이지*/
    @GetMapping("checks")
    public ModelAndView checks(){
        ModelAndView mav = new ModelAndView("in/checks");

        return mav;
    }

    /** 영업검수 페이지 발주단위 */
    @GetMapping("checksPoPop/{poIdx}")
    public ModelAndView checksPoPop(@PathVariable int poIdx){
        ModelAndView mav = new ModelAndView("in/checks_poPop");
        mav.addObject("ig", inGroupRepo.findInGroupByPoPoIdx(poIdx));
        mav.addObject("poProduct", poMapper.selectPoProductByPoIdx(poIdx));
//        mav.addObject("poReceived", poMapper.selectPoReceivedByPoIdx(poIdx));
        return mav;
    }
}

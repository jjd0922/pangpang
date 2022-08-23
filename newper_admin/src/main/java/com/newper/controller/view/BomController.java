package com.newper.controller.view;

import com.newper.entity.Product;
import com.newper.exception.MsgException;
import com.newper.mapper.BomMapper;
import com.newper.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/bom/")
@RequiredArgsConstructor
public class BomController {

    private final ProductRepo productRepo;
    private final BomMapper bomMapper;

    @GetMapping("")
    public ModelAndView bom() {
        ModelAndView mav = new ModelAndView("bom/bom");

        return mav;
    }

    @GetMapping("bomPop")
    public ModelAndView newBom() {
        ModelAndView mav = new ModelAndView("bom/bomPop");

        return mav;
    }

    @GetMapping("bomPop/{pIdx}")
    public ModelAndView bomDetail(@PathVariable Integer pIdx) {
        ModelAndView mav = new ModelAndView("bom/bomPop");

        Product product = productRepo.findById(pIdx).orElseThrow(() -> new MsgException("존재하지 않는 bom 모상품입니다."));
        mav.addObject("mProduct", product);
        mav.addObject("cProduct", bomMapper.selectBomChild(pIdx));
        return mav;
    }

}

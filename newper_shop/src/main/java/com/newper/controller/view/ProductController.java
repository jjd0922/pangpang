package com.newper.controller.view;

import com.newper.dto.ParamMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/product/")
@Controller
@RequiredArgsConstructor
public class ProductController {

    /* product - 카테고리 대분류/중분류 목록페이지 */
    @GetMapping(value = "category")
    public ModelAndView category(Integer scateIdx, ParamMap paramMap){
        ModelAndView mav = new ModelAndView("product/category");

        mav.addObject("scateIdx", scateIdx);
        return mav;
    }
}

package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/shopProduct/")
@Controller
@RequiredArgsConstructor
public class ShopProductController {

    @GetMapping("{idx}")
    public ModelAndView idx(@PathVariable("idx") long spIdx){
        ModelAndView mav = new ModelAndView("shopProduct/idx");

        return mav;
    }
}

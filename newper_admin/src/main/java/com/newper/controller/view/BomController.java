package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/bom/")
@RequiredArgsConstructor
public class BomController {

    @GetMapping("")
    public ModelAndView bom() {
        ModelAndView mav = new ModelAndView("bom/bom");

        return mav;
    }
}

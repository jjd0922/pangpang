package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/warehouse/")
@RequiredArgsConstructor
public class WarehouseController {

    @GetMapping("")
    public ModelAndView warehouse() {
        ModelAndView mav = new ModelAndView("/warehouse/warehouse");

        return mav;
    }

}

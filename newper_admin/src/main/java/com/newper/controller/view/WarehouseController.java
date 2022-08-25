package com.newper.controller.view;

import com.newper.repository.WarehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/warehouse/")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseRepo warehouseRepo;

    @GetMapping("")
    public ModelAndView warehouse() {
        ModelAndView mav = new ModelAndView("/warehouse/warehouse");

        return mav;
    }

    @GetMapping("warehousePop")
    public ModelAndView newWarehousePop() {
        ModelAndView mav = new ModelAndView("/warehouse/warehousePop");

        return mav;
    }
    
    @GetMapping("warehousePop/{whIdx}")
    public ModelAndView warehouseDetailPop(@PathVariable Integer whIdx) {
        ModelAndView mav = new ModelAndView("/warehouse/warehousePop");

        mav.addObject("warehouse", warehouseRepo.findWarehouseByWhIdx(whIdx));
        return mav;
    }
    


}

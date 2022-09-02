package com.newper.controller.view;

import com.newper.constant.OLocation;
import com.newper.mapper.OrderMapper;
import com.newper.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@RequestMapping(value = "/order/")
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;



    /**통합주문관리*/
    @GetMapping("")
    public ModelAndView order(){
        ModelAndView mav = new ModelAndView("order/order");

        return mav;
    }

    @GetMapping("detail")
    public ModelAndView detail(){
        ModelAndView mav = new ModelAndView("order/detail");
        return mav;
    }

    @GetMapping("detail/{oIdx}")
    public ModelAndView detail(@PathVariable int oIdx){
        ModelAndView mav = new ModelAndView("order/detail");

        Map<String, Object> map = orderMapper.selectOrderDetail(oIdx);
        String o_location = OLocation.valueOf(map.get("O_LOCATION")+"").getOption();
        map.put("O_LOCATION",o_location);
        mav.addObject("order", map);

        return mav;
    }




}

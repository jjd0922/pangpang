package com.newper.controller.view;

import com.newper.constant.OLocation;
import com.newper.mapper.OrdersMapper;
import com.newper.repository.OrdersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


@RequestMapping(value = "/orders/")
@Controller
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersMapper ordersMapper;
    private final OrdersRepo ordersRepo;



    /**통합주문관리*/
    @GetMapping("")
    public ModelAndView order(){
        ModelAndView mav = new ModelAndView("orders/order");

        return mav;
    }

    @GetMapping("detail")
    public ModelAndView detail(){
        ModelAndView mav = new ModelAndView("orders/detail");
        return mav;
    }

    @GetMapping("detail/{oIdx}")
    public ModelAndView detail(@PathVariable int oIdx){
        ModelAndView mav = new ModelAndView("orders/detail");

        Map<String, Object> map = ordersMapper.selectOrderDetail(oIdx);
        String o_location = OLocation.valueOf(map.get("O_LOCATION")+"").getOption();
        map.put("O_LOCATION",o_location);
        mav.addObject("order", map);

        List<Map<String, Object>> gs = ordersMapper.selectGoodsStockDetailByOIdx(oIdx);

        mav.addObject("gs",gs);



        return mav;
    }

    @GetMapping("shop/order")
    public ModelAndView shopOrder(){
        ModelAndView mav = new ModelAndView("order/shop_order");
        return mav;
    }




}

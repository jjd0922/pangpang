package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.OrderMapper;
import com.newper.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/order/")
@RestController
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    /**통합주문관리 데이터테이블*/
    @PostMapping("order.dataTable")
    public ReturnDatatable order(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        List<Map<String, Object>> order = orderMapper.selectOrderDatatable(paramMap.getMap());
        int total = orderMapper.countOrderDatatable(paramMap.getMap());
        returnDatatable.setData(order);
        returnDatatable.setRecordsTotal(total);

        return returnDatatable;
    }

    /**사방넷 주문수집*/
    @PostMapping("sabangOrder.ajax")
    public ReturnMap sabangOrder(String startDate, String endDate){
        ReturnMap rm = new ReturnMap();
        orderService.sabangOrder("20220801", "20220815");

        return  rm;
    }

}

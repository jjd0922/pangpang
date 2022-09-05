package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.mapper.OrdersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/orders/")
@RestController
@RequiredArgsConstructor
public class OrdersRestController {

    private final OrdersMapper ordersMapper;

    /**통합주문관리 데이터테이블*/
    @PostMapping("order.dataTable")
    public ReturnDatatable order(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        List<Map<String, Object>> order = ordersMapper.selectOrderDatatable(paramMap.getMap());
        int total = ordersMapper.countOrderDatatable(paramMap.getMap());
        returnDatatable.setData(order);
        returnDatatable.setRecordsTotal(total);

        return returnDatatable;
    }

}

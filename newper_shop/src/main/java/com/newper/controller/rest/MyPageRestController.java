package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.entity.Customer;
import com.newper.mapper.OrdersMapper;
import com.newper.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/myPage/")
public class MyPageRestController {
    private final OrdersMapper ordersMapper;

    @Autowired
    private ShopSession shopSession;

    private final CustomerRepo customerRepo;

    /** 전체주문내역 list */
    @PostMapping("/productList.ajax")
    public ReturnDatatable productList(ParamMap paramMap) {
        System.out.println(paramMap.getMap());
        ReturnDatatable rd = new ReturnDatatable("전체주문내역");
        paramMap.put("CU_IDX",shopSession.getIdx());
        int cu_idx = paramMap.getInt("CU_IDX");
        System.out.println("cu_idx = " + cu_idx);




//        rd.setData(ordersMapper.selectOrderGsListByCuIdx());
        return rd;
    }
}

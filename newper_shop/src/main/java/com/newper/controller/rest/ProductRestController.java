package com.newper.controller.rest;

import com.newper.mapper.ShopProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product/")
public class ProductRestController {

    private final ShopProductMapper shopProductMapper;

    @PostMapping("best/{scateIdx}.load")
    public List<Map<String,Object>> todayBestShopProduct(@PathVariable(value = "scateIdx", required = false) Integer scateIdx){
        List<Map<String,Object>> returnMapList = new ArrayList<>();


        return returnMapList;
    }
}

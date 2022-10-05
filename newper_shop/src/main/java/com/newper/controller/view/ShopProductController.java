package com.newper.controller.view;

import com.newper.component.ShopSession;
import com.newper.constant.SpState;
import com.newper.entity.Category;
import com.newper.entity.ShopProduct;
import com.newper.exception.MsgException;
import com.newper.mapper.CategoryMapper;
import com.newper.mapper.ShopProductMapper;
import com.newper.repository.ShopProductRepo;
import com.newper.service.ShopProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping(value = "/shopProduct/")
@Controller
@RequiredArgsConstructor
public class ShopProductController {

    @Autowired
    private ShopSession shopSession;

    private final ShopProductService shopProductService;
    private final CategoryMapper categoryMapper;
    private final ShopProductMapper shopProductMapper;

    @GetMapping("{idx}")
    public ModelAndView idx(@PathVariable("idx") long spIdx){
        ModelAndView mav = new ModelAndView("shopProduct/idx");



        ShopProduct shopProduct = shopProductService.selectShopProductInfo(spIdx, shopSession.getShopIdx());
        mav.addObject("sp", shopProduct);

        //thumbnail list


        mav.addObject("scate", categoryMapper.selectShopCategoryBySp(spIdx));

        List<Map<String, Object>> spoList = shopProductMapper.selectShopProductOptionList(shopProduct.getSpIdx());

        // key :spa_idx, value:  spo list
        Map<Object, List<Map<String, Object>>> spa_spo = spoList.stream().collect(Collectors.groupingBy(map -> map.get("SPO_SPA_IDX")));
        mav.addObject("spoMap", spa_spo);
        // key :spa_idx, value:  max spoDepth
        Map<Object, Integer> spa_spo_max = new HashMap<>();
        for (Map<String, Object> spoMap : spoList) {
            Object spo_spa_idx = spoMap.get("SPO_SPA_IDX");
            Integer depth = spa_spo_max.get(spo_spa_idx);
            if (depth == null) {
                depth = 1;
            }
            depth = Math.max(depth, Integer.parseInt(spoMap.get("SPO_DEPTH") + ""));
            spa_spo_max.put(spo_spa_idx, depth);
        }
        mav.addObject("spoMax", spa_spo_max);

        return mav;
    }

}

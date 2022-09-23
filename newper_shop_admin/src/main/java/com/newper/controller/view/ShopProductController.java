package com.newper.controller.view;

import com.newper.mapper.ShopCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/shopProduct/")
@Controller
@RequiredArgsConstructor
public class ShopProductController {

    private final ShopCategoryMapper shopCategoryMapper;

    /**상품 관리*/
    @GetMapping("")
    public ModelAndView product(){
        ModelAndView mav = new ModelAndView("shopProduct/shopProduct");

        return mav;
    }

    @GetMapping("detail")
    public ModelAndView detail(){
        ModelAndView mav = new ModelAndView("shopProduct/detail");
        List<Map<String, Object>> shopCategory = shopCategoryMapper.selectShopCategoryDatatableByParent();
        List<Map<String, Object>> category = shopCategoryMapper.selectCategoryListByParent();
        mav.addObject("shopCategory",shopCategory);
        mav.addObject("category",category);

        return mav;
    }


}

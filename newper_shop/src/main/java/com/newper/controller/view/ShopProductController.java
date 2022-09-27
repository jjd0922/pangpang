package com.newper.controller.view;

import com.newper.component.ShopSession;
import com.newper.constant.SpState;
import com.newper.entity.Category;
import com.newper.entity.ShopProduct;
import com.newper.exception.MsgException;
import com.newper.mapper.CategoryMapper;
import com.newper.repository.ShopProductRepo;
import com.newper.service.ShopProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/shopProduct/")
@Controller
@RequiredArgsConstructor
public class ShopProductController {

    @Autowired
    private ShopSession shopSession;

    private final ShopProductService shopProductService;
    private final CategoryMapper categoryMapper;

    @GetMapping("{idx}")
    public ModelAndView idx(@PathVariable("idx") long spIdx){
        ModelAndView mav = new ModelAndView("shopProduct/idx");



        ShopProduct shopProduct = shopProductService.selectShopProductInfo(spIdx, shopSession.getShopIdx());
        mav.addObject("sp", shopProduct);
        mav.addObject("scate", categoryMapper.selectShopCategoryBySp(spIdx));

        return mav;
    }
}

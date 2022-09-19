package com.newper.controller.view;


import com.newper.component.Common;
import com.newper.entity.ShopCategory;
import com.newper.exception.MsgException;
import com.newper.mapper.ShopCategoryMapper;
import com.newper.repository.ShopCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/product/")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ShopCategoryRepo shopCategoryRepo;
private final ShopCategoryMapper shopCategoryMapper;

    /**상품 관리*/
    @GetMapping("")
    public ModelAndView product(){
        ModelAndView mav = new ModelAndView("product/product");

        return mav;
    }

    @GetMapping("detail")
    public ModelAndView detail(){
        ModelAndView mav = new ModelAndView("product/detail");
        List<Map<String, Object>> shopCategory = shopCategoryMapper.selectShopCategoryDatatableByParent();
        List<Map<String, Object>> category = shopCategoryMapper.selectCategoryListByParent();
        mav.addObject("shopCategory",shopCategory);
        mav.addObject("category",category);

        return mav;
    }

    /**전시대분류 관리*/
    @GetMapping("shopCategory")
    public ModelAndView category(){
        ModelAndView mav = new ModelAndView("product/shop_category");

        return mav;
    }

    /**전시대분류 등록*/
    @GetMapping("shopCategoryDetail")
    public ModelAndView shopCategoryDetail(){
        ModelAndView mav = new ModelAndView("product/shop_category_detail");

        return mav;
    }

    /**전시대분류 상세*/
    @GetMapping("shopCategoryDetail/{scateIdx}")
    public ModelAndView shopCategoryDetail(@PathVariable int scateIdx){
        ModelAndView mav = new ModelAndView("product/shop_category_detail");
        ShopCategory shopCategory = shopCategoryRepo.findById(scateIdx).orElseThrow(() -> new MsgException("존재하지 않는 전시대분류입니다."));
        String image = shopCategory.getScateImage();
        image= Common.summernoteContent(image);

        mav.addObject("image", image);
        mav.addObject("shopCategory", shopCategory);

        return mav;
    }

    /**전시중분류 등록*/
    @GetMapping("shopCategory/{scateIdx}/detail")
    public ModelAndView shopCategoryDetail2(@PathVariable int scateIdx){
        ModelAndView mav = new ModelAndView("product/shop_category_detail2");
        mav.addObject("SCATE_IDX",scateIdx);
        return mav;
    }



}

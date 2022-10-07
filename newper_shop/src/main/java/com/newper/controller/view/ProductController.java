package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RequestMapping(value = "/product/")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final CategoryMapper categoryMapper;

    /* product - 카테고리 대분류/중분류 목록페이지 */
    @GetMapping(value = "category")
    public ModelAndView category(ParamMap paramMap){
        ModelAndView mav = new ModelAndView("product/category");

        Map<String,Object> cateMenuName = categoryMapper.selectCategoryMenuName(paramMap.getMap());
        mav.addObject("cateMenuName", cateMenuName);
        if(paramMap.containsKey("scateIdx")){
            mav.addObject("scateIdx",paramMap.getInt("scateIdx"));
        }
        if(paramMap.containsKey("parentCateIdx")){
            mav.addObject("parentCateIdx",paramMap.getInt("parentCateIdx"));
        }
        if(paramMap.containsKey("cateIdx")){
            mav.addObject("cateIdx",paramMap.getInt("cateIdx"));
        }
        return mav;
    }
}

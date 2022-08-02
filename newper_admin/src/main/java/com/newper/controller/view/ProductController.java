package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/product/")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final CategoryRepo categoryRepo;

    @GetMapping("category")
    public ModelAndView category(){
        ModelAndView mav = new ModelAndView("product/category");

        return mav;
    }

    @GetMapping("category/categoryCreate")
    public ModelAndView categoryCreate(ParamMap paramMap){
        ModelAndView mav = new ModelAndView("product/category/category_create");

        return mav;
    }
}

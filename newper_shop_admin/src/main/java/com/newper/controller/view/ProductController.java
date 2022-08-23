package com.newper.controller.view;

import com.newper.component.Common;
import com.newper.constant.CateType;
import com.newper.dto.ParamMap;
import com.newper.entity.Category;
import com.newper.entity.GoodsStock;
import com.newper.entity.Product;
import com.newper.exception.MsgException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/product/")
@Controller
@RequiredArgsConstructor
public class ProductController {




    /**상품 관리*/
    @GetMapping("")
    public ModelAndView category(){
        ModelAndView mav = new ModelAndView("product/ product");

        return mav;
    }



}

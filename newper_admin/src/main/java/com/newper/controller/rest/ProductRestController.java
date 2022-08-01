package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.Category;
import com.newper.mapper.CategoryMapper;
import com.newper.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/product/")
@Controller
@RequiredArgsConstructor
public class ProductRestController {

    private final CategoryRepo categoryRepo;

    private final CategoryMapper categoryMapper;

    @PostMapping("category/parent.dataTable")
    public ReturnDatatable categoryFirst(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();

        List<Map<String, Object>> cpList = categoryMapper.selectCategoryListByParent();
        System.out.println("return : "+cpList);
        returnDatatable.setData(cpList);
        returnDatatable.setRecordsTotal(cpList.size());
        return returnDatatable;
    }

}

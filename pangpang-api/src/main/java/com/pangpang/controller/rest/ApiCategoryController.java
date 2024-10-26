package com.pangpang.controller.rest;

import com.pangpang.dto.ReturnMap;
import com.pangpang.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/api/category/")
@RestController
@RequiredArgsConstructor
public class ApiCategoryController {

    private final CategoryMapper categoryMapper;

    @GetMapping("list.ajax")
    public ReturnMap boardSearchList(@RequestParam(value = "CATE_IDX",required = false) String cateIdx,
                                     @RequestParam(value = "CATE_PARENT_IDX",required = false) String cateParentIdx,
                                     @RequestParam(value = "CATE_TYPE",required = false) String cateType,
                                     @RequestParam(value = "CATE_DEPTH",required = false) String cateDepth,
                                     @RequestParam(value = "CATE_NAME",required = false) String cateName) {
        ReturnMap rm = new ReturnMap();
        List<Map<String, Object>> list = categoryMapper.selectCategoryList(cateIdx,cateParentIdx,cateType,cateDepth,cateName);
        rm.put("list",list);
        return rm;
    }
}

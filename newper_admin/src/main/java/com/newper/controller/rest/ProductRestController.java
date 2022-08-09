package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.CategoryMapper;
import com.newper.mapper.ProductMapper;
import com.newper.repository.CategoryRepo;
import com.newper.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/product/")
@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    /**category 대분류 dataTable*/
    @PostMapping("category/parent.dataTable")
    public ReturnDatatable categoryParent(){
        ReturnDatatable returnDatatable = new ReturnDatatable();

        List<Map<String, Object>> cpList = categoryMapper.selectCategoryDatatableByParent();
        returnDatatable.setData(cpList);
        returnDatatable.setRecordsTotal(cpList.size());
        return returnDatatable;
    }

    /**category 중분류 dataTable*/
    @PostMapping("category/children.dataTable")
    public ReturnDatatable categoryChildren(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        Integer CATE_IDX=null;
        if(paramMap.get("CATE_IDX")!=null&&!paramMap.get("CATE_IDX").equals("")){
            CATE_IDX=Integer.parseInt(paramMap.get("CATE_IDX")+"");
        }
        List<Map<String,Object>> ccList = categoryMapper.selectCategoryDatatableByChildren(CATE_IDX);
        returnDatatable.setData(ccList);
        returnDatatable.setRecordsTotal(ccList.size());

        return returnDatatable;

    }

    /**category 소분류 dataTable*/
    @PostMapping("category/youngest.dataTable")
    public ReturnDatatable categoryYoungest(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        Integer CATE_IDX=null;
        if(paramMap.get("CATE_IDX")!=null&&!paramMap.get("CATE_IDX").equals("")){
            CATE_IDX=Integer.parseInt(paramMap.get("CATE_IDX")+"");
        }
        List<Map<String,Object>> ccList = categoryMapper.selectCategoryDatatableByChildren(CATE_IDX);
        returnDatatable.setData(ccList);
        returnDatatable.setRecordsTotal(ccList.size());

        return returnDatatable;

    }


    /**카테고리 순서 변경*/
    @PostMapping("category/order.ajax")
    public ReturnMap categoryFirstOrder(Integer CATE_IDX[]){
        ReturnMap rm = new ReturnMap();
        categoryService.categoryOrder(CATE_IDX);

        return rm;
    }

    /**카테고리 삭제*/
    @PostMapping("category/delete.ajax")
    public ReturnMap categoryDelete(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        int CATE_IDX = Integer.parseInt(paramMap.get("CATE_IDX")+"");
        try {
            categoryService.categoryDelete(CATE_IDX);
            rm.setMessage("삭제 완료");
        }catch (DataIntegrityViolationException de){
            rm.setMessage("삭제할 수 없는 카테고리입니다. (카테고리 사용중)");
        }

        return rm;
    }


    /** 카테고리 select **/
    @PostMapping("category/selectCategory.ajax")
    public List<Map<String, Object>> categorySelect(ParamMap paramMap) {
        return categoryMapper.selectCategory(paramMap.getMap());
    }

    /** product DataTable */
    @PostMapping("product.dataTable")
    public ReturnDatatable productDataTable(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        returnDatatable.setData(productMapper.selectProductDataTalbe(paramMap.getMap()));
        returnDatatable.setRecordsTotal(productMapper.countProductDataTable(paramMap.getMap()));
        return returnDatatable;
    }

    /**브랜드 dataTable*/
    @PostMapping("brand.dataTable")
    public ReturnDatatable CategoryDatatableByBrand(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        List<Map<String,Object>> bList = categoryMapper.selectCategoryDatatableByBrand(paramMap.getMap());
        int total = categoryMapper.countCategoryDatatableByBrand(paramMap.getMap());
        returnDatatable.setData(bList);
        returnDatatable.setRecordsTotal(total);
        return returnDatatable;
    }

    /**카테고리(브랜드) 노출 수정*/
    @PostMapping("categoryDisplay.ajax")
    public ReturnMap categoryDisplay(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        rm.setMessage(categoryService.categoryDisplayUpdate(paramMap));

        return rm;
    }

    /**카테고리(브랜드) 삭제*/
    @PostMapping("brandDelete.ajax")
    public ReturnMap brandDelete(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        rm.setMessage(categoryService.brandDelete(paramMap));
        return rm;
    }


}

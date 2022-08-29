package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.CategoryMapper;
import com.newper.mapper.ShopCategoryMapper;
import com.newper.mapper.ShopProductMapper;
import com.newper.service.ShopCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/product/")
@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final ShopCategoryMapper shopCategoryMapper;
    private final ShopCategoryService shopCategoryService;
    private final ShopProductMapper shopProductMapper;
    private final CategoryMapper categoryMapper;



    /**shop_category 대분류 dataTable*/
    @PostMapping("parent.dataTable")
    public ReturnDatatable categoryParent(){
        ReturnDatatable returnDatatable = new ReturnDatatable();

        List<Map<String, Object>> cpList = shopCategoryMapper.selectShopCategoryDatatableByParent();
        returnDatatable.setData(cpList);
        returnDatatable.setRecordsTotal(cpList.size());
        return returnDatatable;
    }

    /**shop_category 중분류 dataTable*/
    @PostMapping("children.dataTable")
    public ReturnDatatable categoryChildren(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        Integer SCATE_IDX=null;
        if(paramMap.get("SCATE_IDX")!=null&&!paramMap.get("SCATE_IDX").equals("")){
            SCATE_IDX=paramMap.getInt("SCATE_IDX");
        }
        System.out.println("SCATE_IDX : "+SCATE_IDX);
        List<Map<String,Object>> ccList = categoryMapper.selectCategoryDatatableByChildren(SCATE_IDX);
        returnDatatable.setData(ccList);
        returnDatatable.setRecordsTotal(ccList.size());

        return returnDatatable;

    }

    /**shop_category 순서 변경*/
    @PostMapping("order.ajax")
    public ReturnMap shopCategoryOrder(Integer SCATE_IDX[]){
        ReturnMap rm = new ReturnMap();
        shopCategoryService.shopCategoryOrder(SCATE_IDX);

        return rm;
    }

    /**카테고리 순서 변경*/
    @PostMapping("category/order.ajax")
    public ReturnMap categoryOrder(Integer CSC_IDX[]){
        ReturnMap rm = new ReturnMap();
        shopCategoryService.categoryOrder(CSC_IDX);

        return rm;
    }

    /**전시대분류 저장*/
    @PostMapping("shopCategorySave.ajax")
    public ReturnMap shopCategorySave(ParamMap paramMap, MultipartFile SCATE_ICON, MultipartFile SCATE_THUMBNAIL){
        ReturnMap rm = new ReturnMap();
        paramMap.put("SCATE_DEPTH",1);
        Integer res=shopCategoryService.shopProductInsert(paramMap, SCATE_ICON, SCATE_THUMBNAIL);
        if(res>0){
            rm.setMessage("저장되었습니다.");
        }
        return rm;
    }

    /**전시대분류 카테고리 중분류 리스트*/
    @PostMapping("twoDepth.dataTable")
    public ReturnDatatable twoDepth(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        List<Map<String, Object>> list = shopCategoryMapper.selectCategoryDatatableByTwoDetph(paramMap.getMap());
        returnDatatable.setData(list);
        returnDatatable.setRecordsTotal(list.size());

        return returnDatatable;
    }

    /**전시대분류 중분류 저장*/
    @PostMapping("{SCATE_IDX}/shopCategory.ajax")
    public ReturnMap shopCategory(@PathVariable int SCATE_IDX,ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        paramMap.put("SCATE_IDX",SCATE_IDX);
        rm.setMessage(shopCategoryService.categoryShopCategoryInsert(paramMap));
        return rm;
    }

    /**전시대분류 삭제*/
    @PostMapping("shopCategory/delete.ajax")
    public ReturnMap shopCategoryDelete(int SCATE_IDX){
        ReturnMap rm = new ReturnMap();
        try {
            shopCategoryService.shopCategoryDelete(SCATE_IDX);
            rm.setMessage("삭제 완료");
        }catch (Exception e){}
        return rm;
    }

    /**전시대분류 중분류 삭제*/
    @PostMapping("categoryShopCategory/delete.ajax")
    public ReturnMap categoryShopCategoryDelete(int CSC_IDX){
        ReturnMap rm = new ReturnMap();
        try {
            shopCategoryService.categoryShopCategoryDelete(CSC_IDX);
            rm.setMessage("삭제 완료");
        }catch (Exception e){}
        return rm;
    }

    /**shop_product datatable*/
    @PostMapping("shopProduct.datatable")
    public ReturnDatatable shopProduct(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        List<Map<String, Object>> list = shopProductMapper.selectShopProductDatatable(paramMap.getMap());
        returnDatatable.setData(list);
        returnDatatable.setRecordsTotal(shopProductMapper.countShopProductDatatable(paramMap.getMap()));

        return returnDatatable;
    }

    /**상위 카테고리의 하위 카테고리*/
    @PostMapping("category/parent.ajax")
    public ReturnMap parent(int CATE_IDX){
        ReturnMap rm = new ReturnMap();
        Map<String,Object> map = new HashMap<>();
        map.put("cateIdx",CATE_IDX);
//        List<Map<String,Object>> list = categoryMapper.selectCategory(map);
//        rm.put("list",list);
        return rm;
    }


}

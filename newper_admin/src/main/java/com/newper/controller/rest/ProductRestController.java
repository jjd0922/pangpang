package com.newper.controller.rest;

import com.newper.constant.PState;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.CategoryMapper;
import com.newper.mapper.ProductMapper;
import com.newper.repository.CategoryRepo;
import com.newper.service.CategoryService;
import com.newper.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/product/")
@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final ProductService productService;


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
        ReturnDatatable returnDatatable = new ReturnDatatable("상품관리");
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

    /**상위 카테고리의 하위 카테고리*/
    @PostMapping("category/parent.ajax")
    public ReturnMap parent(int CATE_IDX){
        ReturnMap rm = new ReturnMap();
        Map<String,Object> map = new HashMap<>();
        map.put("cateIdx",CATE_IDX);
        List<Map<String,Object>> list = categoryMapper.selectCategory(map);
        rm.put("list",list);
        return rm;
    }

    /**상품저장*/
    @PostMapping("productSave.ajax")
    public ReturnMap productSave(ParamMap paramMap, MultipartFile P_THUMB_FILE1, MultipartFile P_THUMB_FILE2, MultipartFile P_THUMB_FILE3, MultipartFile P_THUMB_FILE4, MultipartFile P_THUMB_FILE5, MultipartFile P_THUMB_FILE6){
        ReturnMap rm = new ReturnMap();

        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        for(int i=1; i<10; i++){
            map1.put("P_NAVER"+i, paramMap.get("P_NAVER"+i));
        }
        paramMap.put("P_NAVER", map1);

        for(int y=1; y<4; y++){
            map2.put("P_INFO_KEY"+y, paramMap.get("P_INFO_KEY"+y));
            map2.put("P_INFO_VALUE"+y, paramMap.get("P_INFO_VALUE"+y));

            String p_option_key="";
            String p_option_value="";
            if(paramMap.get("p_option_key"+y)!=null){
                p_option_key=paramMap.get("p_option_key"+y)+"";
                p_option_value=paramMap.get("p_option_value"+y)+"";
            }
            map3.put("p_option_key"+y,p_option_key);
            map3.put("p_option_value"+y,p_option_value);

        }
        paramMap.put("P_INFO",map2);
        paramMap.put("P_OPTION",map3);

        paramMap.put("P_STATE", PState.PROTO);
        paramMap.put("P_COST", paramMap.get("P_COST").toString().replaceAll("[^0-9.]", ""));
        paramMap.put("P_RETAIL_PRICE", paramMap.get("P_RETAIL_PRICE").toString().replaceAll("[^0-9.]", ""));
        paramMap.put("P_SELL_PRICE", paramMap.get("P_SELL_PRICE").toString().replaceAll("[^0-9.]", ""));
        paramMap.put("P_DEL_PRICE", paramMap.get("P_DEL_PRICE").toString().replaceAll("[^0-9.]", ""));
        System.out.println(paramMap.getMap());
        int res = productService.productSave(paramMap,P_THUMB_FILE1,P_THUMB_FILE2,P_THUMB_FILE3,P_THUMB_FILE4,P_THUMB_FILE5,P_THUMB_FILE6);
        if(res>0){
            rm.setMessage("저장되었습니다.");
        }
        return rm;
    }

    /**상품수정*/
    @PostMapping("{P_IDX}/productUpdate.ajax")
    public ReturnMap productUpdate(@PathVariable int P_IDX, ParamMap paramMap, MultipartFile P_THUMB_FILE1, MultipartFile P_THUMB_FILE2, MultipartFile P_THUMB_FILE3, MultipartFile P_THUMB_FILE4, MultipartFile P_THUMB_FILE5, MultipartFile P_THUMB_FILE6){
        ReturnMap rm = new ReturnMap();
        paramMap.put("P_IDX",P_IDX);
        System.out.println(paramMap.getMap());
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        for(int i=1; i<10; i++){
            map1.put("P_NAVER"+i, paramMap.get("P_NAVER"+i));
        }
        paramMap.put("P_NAVER", map1);

        for(int y=1; y<4; y++){
            map2.put("P_INFO_KEY"+y, paramMap.get("P_INFO_KEY"+y));
            map2.put("P_INFO_VALUE"+y, paramMap.get("P_INFO_VALUE"+y));

            String p_option_key="";
            String p_option_value="";
            if(paramMap.get("p_option_key"+y)!=null){
                p_option_key=paramMap.get("p_option_key"+y)+"";
                p_option_value=paramMap.get("p_option_value"+y)+"";
            }
            map3.put("p_option_key"+y,p_option_key);
            map3.put("p_option_value"+y,p_option_value);

        }
        paramMap.put("P_INFO",map2);
        paramMap.put("P_OPTION",map3);

        paramMap.put("P_STATE", PState.PROTO);
        paramMap.put("P_COST", paramMap.get("P_COST").toString().replaceAll("[^0-9.]", ""));
        paramMap.put("P_RETAIL_PRICE", paramMap.get("P_RETAIL_PRICE").toString().replaceAll("[^0-9.]", ""));
        paramMap.put("P_SELL_PRICE", paramMap.get("P_SELL_PRICE").toString().replaceAll("[^0-9.]", ""));
        paramMap.put("P_DEL_PRICE", paramMap.get("P_DEL_PRICE").toString().replaceAll("[^0-9.]", ""));
        System.out.println(paramMap.getMap());
        int res = productService.productUpdate(paramMap,P_THUMB_FILE1,P_THUMB_FILE2,P_THUMB_FILE3,P_THUMB_FILE4,P_THUMB_FILE5,P_THUMB_FILE6);
        if(res>0){
            System.out.println(res);
            rm.setMessage("저장되었습니다.");
        }

        return rm;
    }

    /**재고관리 데이터테이블*/
    @PostMapping("goodsStock.dataTable")
    public ReturnDatatable goodsStock(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable("재고관리");
        returnDatatable.setData(productMapper.selectGoodsStockDataTable(paramMap.getMap()));
        returnDatatable.setRecordsTotal(productMapper.countGoodsStockDataTable(paramMap.getMap()));
        return returnDatatable;
    }


}

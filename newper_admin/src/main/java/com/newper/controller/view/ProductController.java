package com.newper.controller.view;

import com.newper.constant.CateType;
import com.newper.dto.ParamMap;
import com.newper.entity.Category;
import com.newper.entity.Product;
import com.newper.exception.MsgException;
import com.newper.mapper.CategoryMapper;
import com.newper.repository.CategoryRepo;
import com.newper.repository.ProductRepo;
import com.newper.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/product/")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    /**카테고리 관리*/
    @GetMapping("category")
    public ModelAndView category(){
        ModelAndView mav = new ModelAndView("product/category");

        return mav;
    }

    /**카테고리 등록 팝업*/
    @GetMapping("category/categoryCreate/{cate_depth}")
    public ModelAndView categoryCreate(ParamMap paramMap,@PathVariable int cate_depth){
        ModelAndView mav = new ModelAndView("product/category/categoryCreate");
        mav.addObject("cate_depth",cate_depth);
        if(cate_depth > 1){
            mav.addObject("parent",categoryMapper.selectCategoryListByCateDepth(cate_depth-1));
        }
        return mav;
    }

    /**카테고리 등록*/
    @PostMapping("category/categoryCreate/{cate_depth}")
    public ModelAndView categoryCatePost(@PathVariable int cate_depth,ParamMap paramMap, MultipartFile CATE_ICON, MultipartFile CATE_THUMBNAIL){
        ModelAndView mav = new ModelAndView("main/alertClose");
        paramMap.put("CATE_DEPTH",cate_depth);
        paramMap.put("CATE_TYPE", CateType.CATEGORY);
        categoryService.categoryInsert(paramMap,CATE_ICON,CATE_THUMBNAIL);
        mav.addObject("msg","등록 완료");
        return mav;
    }

    /**카테고리 상세 팝업*/
    @GetMapping("category/categoryDetail/{cate_idx}")
    public ModelAndView categoryDetail(@PathVariable int cate_idx){
        ModelAndView mav = new ModelAndView("product/category/categoryDetail");
        Category category = categoryRepo.findById(cate_idx).orElseThrow(() -> new MsgException("존재하지 않는 카테고리입니다."));

        int depth = category.getCateDepth();

        if(depth==2){
            mav.addObject("parent",categoryMapper.selectCategoryListByCateDepth(1));
        }else if(depth==3){
            mav.addObject("parent",categoryMapper.selectCategoryListByCateDepth(2));
        }


        String image = category.getCateImage();
        image = image.replaceAll("&lt;","<");
        image=image.replaceAll("&#37;","%");
        image=image.replaceAll("&gt;",">");
        image=image.replaceAll("&quot;","\"");
        image=image.replaceAll("<br>",System.getProperty("line.separator"));
        mav.addObject("category",category);
        mav.addObject("image",image);
        return mav;
    }

    /**카테고리 수정*/
    @PostMapping("category/categoryDetail/{cate_idx}")
    public ModelAndView categoryDetailPost(@PathVariable int cate_idx,ParamMap paramMap, MultipartFile CATE_ICON, MultipartFile CATE_THUMBNAIL){
        ModelAndView mav = new ModelAndView("main/alertMove");
        paramMap.put("CATE_IDX",cate_idx);
        categoryService.categoryUpdate(paramMap,CATE_ICON,CATE_THUMBNAIL);
        mav.addObject("msg","수정 완료");
        mav.addObject("loc","/product/category/categoryDetail/"+cate_idx);
        return mav;
    }

    /**브랜드관리*/
    @GetMapping("brand")
    public ModelAndView brand(){
        ModelAndView mav = new ModelAndView("product/brand");

        return mav;
    }

    /**브랜드 등록 팝업*/
    @GetMapping("brand/detail")
    public ModelAndView brandDetail(){
        ModelAndView mav = new ModelAndView("product/brand_detail");
        return mav;
    }

    /**브랜드 등록*/
    @PostMapping("brand/detail")
    public ModelAndView brandDetailPost(ParamMap paramMap, MultipartFile CATE_ICON, MultipartFile CATE_THUMBNAIL){
        ModelAndView mav = new ModelAndView("main/alertClose");
        paramMap.put("CATE_DEPTH",1);
        paramMap.put("CATE_TYPE", CateType.BRAND);
        categoryService.categoryInsert(paramMap,CATE_ICON,CATE_THUMBNAIL);
        mav.addObject("msg","등록 완료");
        return mav;
    }

    /**브랜드 상세 팝업*/
    @GetMapping("brand/{cate_idx}")
    public ModelAndView brandDetail(@PathVariable int cate_idx){
        ModelAndView mav = new ModelAndView("product/brand_detail");

        Category category = categoryRepo.findById(cate_idx).orElseThrow(() -> new MsgException("존재하지 않는 카테고리입니다."));

        String image = category.getCateImage();
        image = image.replaceAll("&lt;","<");
        image=image.replaceAll("&#37;","%");
        image=image.replaceAll("&gt;",">");
        image=image.replaceAll("&quot;","\"");
        image=image.replaceAll("<br>",System.getProperty("line.separator"));

        mav.addObject("category",category);
        mav.addObject("image",image);

        return mav;
    }

    /**브랜드 수정*/
    @PostMapping("brand/{cate_idx}")
    public ModelAndView brandDetailPost(@PathVariable int cate_idx,ParamMap paramMap, MultipartFile CATE_ICON, MultipartFile CATE_THUMBNAIL){
        ModelAndView mav = new ModelAndView("main/alertMove");
        paramMap.put("CATE_IDX",cate_idx);
        categoryService.categoryUpdate(paramMap,CATE_ICON,CATE_THUMBNAIL);
        mav.addObject("msg","수정 완료");
        mav.addObject("loc","/product/brand/"+cate_idx);
        return mav;
    }

    /**상품관리*/
    @GetMapping("")
    public ModelAndView product(){
        ModelAndView mav = new ModelAndView("product/product");
        return mav;
    }

    /**상품관리 등록 페이지*/
    @GetMapping("detail")
    public ModelAndView detail(){
        ModelAndView mav = new ModelAndView("product/detail");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("length",-1);
        mav.addObject("brand", categoryMapper.selectCategoryDatatableByBrand(map));
        mav.addObject("category",categoryMapper.selectCategoryDatatableByParent());

        return mav;
    }
    /**상품관리 수정 페이지*/
    @GetMapping("{pIdx}")
    public ModelAndView detail(@PathVariable Integer pIdx){
        ModelAndView mav = new ModelAndView("product/detail");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("length",-1);
        mav.addObject("brand", categoryMapper.selectCategoryDatatableByBrand(map));

        Product product = productRepo.findById(pIdx).get();
        mav.addObject("product", product);

        return mav;
    }

    /**재고상품관리*/
    @GetMapping("stockProduct")
    public ModelAndView stockProduct(){
        ModelAndView mav = new ModelAndView("product/stockProduct");
        return mav;
    }

    /**재고상품관리 등록*/
    @GetMapping("stockProductCreate")
    public ModelAndView stockProductCreate(){
        ModelAndView mav = new ModelAndView("product/stockProduct/stockProductDetail");
        return mav;
    }



}

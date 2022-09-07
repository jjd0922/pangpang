package com.newper.controller.view;

import com.newper.component.Common;
import com.newper.constant.CateType;
import com.newper.dto.ParamMap;
import com.newper.entity.Category;
import com.newper.entity.Company;
import com.newper.entity.GoodsStock;
import com.newper.entity.Product;
import com.newper.exception.MsgException;
import com.newper.mapper.CategoryMapper;
import com.newper.repository.CategoryRepo;
import com.newper.repository.CompanyRepo;
import com.newper.repository.GoodsStockRepo;
import com.newper.repository.ProductRepo;
import com.newper.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/product/")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final CompanyRepo companyRepo;
    private final GoodsStockRepo goodsStockRepo;

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
    @GetMapping("category/{idx}")
    public ModelAndView categoryDetail(@PathVariable(value = "idx") int cateIdx){
        ModelAndView mav = new ModelAndView("product/category_idx");
        Category category = categoryRepo.findById(cateIdx).orElseThrow(() -> new MsgException("존재하지 않는 카테고리입니다."));

        int depth = category.getCateDepth();

        if(depth==2){
            mav.addObject("parent",categoryMapper.selectCategoryListByCateDepth(1));
        }else if(depth==3){
            mav.addObject("parent",categoryMapper.selectCategoryListByCateDepth(2));
        }

        List<String> list = category.getCateSpecList();
        String spec = "";
        for (int i = 0; i < list.size(); i++) {
            spec += list.get(i);
            if (i + 1 != list.size()) {
                spec += ", ";
            }
        }
        mav.addObject("spec",spec);

        String image = category.getCateImage();
        image=Common.summernoteContent(image);
        mav.addObject("category",category);
        mav.addObject("image",image);

        return mav;
    }

    /**카테고리 수정*/
    @PostMapping("category/{cateIdx}")
    public ModelAndView categoryDetailPost(@PathVariable int cateIdx,ParamMap paramMap, MultipartFile CATE_ICON, MultipartFile CATE_THUMBNAIL){
        ModelAndView mav = new ModelAndView("main/alertMove");
        paramMap.put("CATE_IDX",cateIdx);
        categoryService.categoryUpdate(paramMap,CATE_ICON,CATE_THUMBNAIL);
        mav.addObject("msg","수정 완료");
        mav.addObject("loc","/product/category/"+cateIdx);
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
    @GetMapping("brand/{cateIdx}")
    public ModelAndView brandDetail(@PathVariable int cateIdx){
        ModelAndView mav = new ModelAndView("product/brand_detail");

        Category category = categoryRepo.findById(cateIdx).orElseThrow(() -> new MsgException("존재하지 않는 카테고리입니다."));

        String image = category.getCateImage();
        image= Common.summernoteContent(image);

        mav.addObject("category",category);
        mav.addObject("image",image);

        return mav;
    }

    /**브랜드 수정*/
    @PostMapping("brand/{cateIdx}")
    public ModelAndView brandDetailPost(@PathVariable int cateIdx,ParamMap paramMap, MultipartFile CATE_ICON, MultipartFile CATE_THUMBNAIL){
        ModelAndView mav = new ModelAndView("main/alertMove");
        paramMap.put("CATE_IDX",cateIdx);
        categoryService.categoryUpdate(paramMap,CATE_ICON,CATE_THUMBNAIL);
        mav.addObject("msg","수정 완료");
        mav.addObject("loc","/product/brand/"+cateIdx);
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
        mav.addObject("category",categoryMapper.selectCategoryDatatableByParent());

        Product product = productRepo.findById(pIdx).orElseThrow(() -> new MsgException("존재하지 않는 상품입니다."));

        String content1 = product.getPContent1();
        content1= Common.summernoteContent(content1);

        String content2 = product.getPContent2();
        content2= Common.summernoteContent(content2);

        String content3 = product.getPContent3();
        content3=Common.summernoteContent(content3);

        mav.addObject("content1",content1);
        mav.addObject("content2",content2);
        mav.addObject("content3",content3);

        List<Map<String,Object>> option = product.getPOption();
        int cnt=0;
        for(int i=0; i<option.size(); i++){
            cnt++;
        }
        System.out.println("cnt : "+cnt);
        mav.addObject("optionSize",option.size());

        Map<String, Object> cg = new HashMap<>();
        if(product.getCategory() != null){
            Map<String,Object> category = categoryMapper.selectCategoryDetail(product.getCategory().getCateIdx());
            int depth = Integer.parseInt(category.get("CATE_DEPTH")+"");
            cg.put("depth",depth);
            if(depth==1){
                cg.put("CATE_IDX1",category.get("ori_cate_idx"));
                cg.put("CATE_IDX2","");
                cg.put("CATE_IDX3","");
            }else if(depth==2){
                cg.put("CATE_IDX2",category.get("ori_cate_idx"));
                cg.put("CATE_IDX1",category.get("per_cate_idx1"));
                cg.put("CATE_IDX3","");
            }else if(depth==3){
                cg.put("CATE_IDX3",category.get("ori_cate_idx"));
                cg.put("CATE_IDX2",category.get("per_cate_idx1"));
                cg.put("CATE_IDX1",category.get("per_cate_idx2"));
            }
            mav.addObject("cg",cg);
        }

        Map<String,Object> com = new HashMap<>();
        if (product.getStoreName() != null) {
            com.put("storeName",companyRepo.findById(product.getStoreName().getComIdx()).get().getComName());
        }else{
            com.put("storeName","");
        }
        if (product.getManufactureName() != null) {
            com.put("manufactureName",companyRepo.findById(product.getManufactureName().getComIdx()).get().getComName());
        }else{
            com.put("manufactureName","");
        }
        if (product.getAfterServiceName() != null) {
            com.put("afterServiceName",companyRepo.findById(product.getAfterServiceName().getComIdx()).get().getComName());
        }else{
            com.put("afterServiceName","");
        }

        mav.addObject("com",com);
        mav.addObject("product", product);

        return mav;
    }

    /**재고상품관리*/
    @GetMapping("goodsStock")
    public ModelAndView goodsStock(){
        ModelAndView mav = new ModelAndView("product/goods_stock");
        return mav;
    }

    /**재고상품관리 등록*/
    @GetMapping("goodsStock/detail")
    public ModelAndView goodsStockCreate(){
        ModelAndView mav = new ModelAndView("product/goods_stock_detail");
        return mav;
    }

    /**재고상품관리 상세*/
    @GetMapping("goodsStock/{gsidx}")
    public ModelAndView goodsStockDetail(@PathVariable int gsidx) {
        ModelAndView mav = new ModelAndView("product/goods_stock_detail");

        GoodsStock goodsStock = goodsStockRepo.findGoodsStockByGsIdx(gsidx);
        Product product = goodsStock.getProduct();

        List<Map<String, Object>> po = product.getPOption();

        for(int i=0; i<po.size();i++){
            List<String> val = (List)po.get(i).get("values");
            System.out.println("option"+(i+1));
            mav.addObject("option"+(i+1),val);
        }


        String content = goodsStock.getGsContent();
        content= Common.summernoteContent(content);

        mav.addObject("goodsStock", goodsStock);
        mav.addObject("product", product);
        mav.addObject("content", content);

        return mav;
    }

    @GetMapping("info")
    public ModelAndView info() {
        ModelAndView mav = new ModelAndView("product/info");
        return mav;
    }
    /**재고 상품 관리*/
    @GetMapping("stock")
    public ModelAndView stock() {
        ModelAndView mav = new ModelAndView("product/stock");
        return mav;
    }

}

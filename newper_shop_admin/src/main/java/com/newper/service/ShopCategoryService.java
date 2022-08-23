package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.dto.ParamMap;
import com.newper.entity.Category;
import com.newper.entity.CategoryShopCategory;
import com.newper.entity.ShopCategory;
import com.newper.exception.MsgException;
import com.newper.mapper.ShopCategoryMapper;
import com.newper.repository.CategoryRepo;
import com.newper.repository.CategoryShopCategoryRepo;
import com.newper.repository.ShopCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShopCategoryService {

    private final ShopCategoryRepo shopCategoryRepo;
    private final ShopCategoryMapper shopCategoryMapper;
    private final CategoryRepo categoryRepo;
    private final CategoryShopCategoryRepo categoryShopCategoryRepo;


    @Transactional
    public void shopCategoryOrder(Integer[] scate_idx){
        //마지막은 드래그앤드랍 중복되는 idx들어감
        for (int i = 0; i < scate_idx.length - 1; i++) {
            ShopCategory shopCategory = shopCategoryRepo.findById(scate_idx[i]).orElseThrow(()->new MsgException("존재하지 않는 대분류 입니다."));
            //미노출이 음수이므로 order는 0이 아닌 1부터 시작하게끔
            shopCategory.updateShopCategoryOrder(i+1);
        }
    }

    @Transactional
    public void categoryOrder(Integer[] csc_idx){
        //마지막은 드래그앤드랍 중복되는 idx들어감
        for (int i = 0; i < csc_idx.length - 1; i++) {
            CategoryShopCategory categoryShopCategory = categoryShopCategoryRepo.findById(csc_idx[i]).orElseThrow(()->new MsgException("존재하지 않는 중분류 입니다."));
            //미노출이 음수이므로 order는 0이 아닌 1부터 시작하게끔
            categoryShopCategory.updateCscOrder(i+1);
        }
    }

    @Transactional
    public void shopCategoryDelete(int SCATE_IDX){
        shopCategoryRepo.deleteById(SCATE_IDX);
    }
    @Transactional
    public void categoryShopCategoryDelete(int CSC_IDX){
        categoryShopCategoryRepo.deleteById(CSC_IDX);
    }

    /**카테고리 등록*/
    @Transactional
    public int shopProductInsert(ParamMap paramMap, MultipartFile icon, MultipartFile thumbnail){
        ShopCategory shopCategory = paramMap.mapParam(ShopCategory.class);
        Map<String,Object> map = new HashMap<>();
        Integer cateOrder = shopCategoryMapper.maxShopCategoryOrderBySCateDepth(paramMap.getInt("SCATE_DEPTH"));
        if(cateOrder==null){
            cateOrder=0;
        }
        cateOrder=cateOrder+1;
        shopCategory.setScateOrder(cateOrder);
        shopCategory.setScateNick("");

        if (paramMap.get("SCATE_PARENT_IDX")!=null){
            ShopCategory parentShopCategory = shopCategoryRepo.findById(Integer.parseInt(paramMap.get("SCATE_PARENT_IDX")+"")).get();
            shopCategory.setScateIdx(null);
        }

        String iconFilePath = "";
        String thumbnailFilePath = "";
        if(icon.getSize()!=0){
            iconFilePath = Common.uploadFilePath(icon, "shop_category/icon/", AdminBucket.OPEN);
        }
        if(thumbnail.getSize()!=0){
            thumbnailFilePath = Common.uploadFilePath(thumbnail, "shop_category/thumbnail/",AdminBucket.OPEN);
        }

        shopCategory.setScateIcon(iconFilePath);
        shopCategory.setScateThumbnail(thumbnailFilePath);
        shopCategoryRepo.saveAndFlush(shopCategory);

        return shopCategory.getScateIdx();
    }
//
//    /**카테고리 수정*/
//    @Transactional
//    public int categoryUpdate(ParamMap paramMap, MultipartFile icon, MultipartFile thumbnail){
//        Category category = categoryRepo.findById(paramMap.getInt("CATE_IDX")).get();
//        Category categoryParam = paramMap.mapParam(Category.class);
//        categoryParam.setCateMemo(category.getCateNick());
//        categoryParam.setCateSpecList(category.getCateSpecList());
//        categoryParam.setCateType(category.getCateType());
//        categoryParam.setCateNick(category.getCateNick());
//        categoryParam.setCateOrder(category.getCateOrder());
//        categoryParam.setCateDepth(category.getCateDepth());
//        if(paramMap.get("CATE_PARENT_IDX")!=null){
//            Category parentCategory = categoryRepo.findById(paramMap.getInt("CATE_PARENT_IDX")).get();
//            categoryParam.setParentCategory(parentCategory);
//        }
//
//
//        String iconFilePath = category.getCateIcon();
//        String thumbnailFilePath = category.getCateThumbnail();
//
//        if(icon.getSize()!=0){
//            iconFilePath = Common.uploadFilePath(icon, "category/icon/", AdminBucket.OPEN);
//        }
//        if(thumbnail.getSize()!=0){
//            thumbnailFilePath = Common.uploadFilePath(thumbnail, "category/thumbnail/",AdminBucket.OPEN);
//        }
//        categoryParam.setCateIcon(iconFilePath);
//        categoryParam.setCateThumbnail(thumbnailFilePath);
//        categoryRepo.saveAndFlush(categoryParam);
//
//        return categoryParam.getCateIdx();
//
//    }


    public String categoryShopCategoryInsert(ParamMap paramMap){
        String str = "저장되었습니다.";
        ShopCategory shopCategory = shopCategoryRepo.findById(paramMap.getInt("SCATE_IDX")).orElseThrow(()->new MsgException("존재하지 않는 전시대분류 입니다."));
        String cateIdxs[] = paramMap.get("CATE_IDXS").toString().split(",");
        System.out.println(cateIdxs.length+"=======================");
        CategoryShopCategory categoryShopCategory = paramMap.mapParam(CategoryShopCategory.class);
        for(int i=0; i<cateIdxs.length; i++){
            categoryShopCategory.setCscIdx(null);
            System.out.println("cateIdx : "+cateIdxs[i]);
            Category category = categoryRepo.findById(Integer.parseInt(cateIdxs[i])).orElseThrow(()->new MsgException("존재하지 않는 중분류 입니다."));
            Map<String,Object> sMap = shopCategoryMapper.selectCategoryDetailByTwoDetph(Integer.parseInt(cateIdxs[i]));
            if(sMap!=null&&sMap.get("SCATE_IDX")!=null){
                str="해당 중분류는 "+sMap.get("SCATE_NAME")+" 전시 대분류에 속해 있습니다. 저장시 "+sMap.get("SCATE_NAME")+"에서 제외되고 "+shopCategory.getScateName()+" 전시 대분류 하위로 이동합니다.";
                System.out.println(sMap.get("CSC_IDX"));
                System.out.println(sMap);
                categoryShopCategory.setCscIdx(Integer.parseInt(sMap.get("CSC_IDX")+""));
            }
            System.out.println("==========here=============");
            categoryShopCategory.setCategory(category);
            categoryShopCategory.setShopCategory(shopCategory);
            System.out.println(categoryShopCategory.getShopCategory());
            System.out.println(categoryShopCategory.getCategory());
            System.out.println(categoryShopCategory.getCscIdx());
            categoryShopCategoryRepo.save(categoryShopCategory);
        }


        return str;
    }


}

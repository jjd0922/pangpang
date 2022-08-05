package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.dto.ParamMap;
import com.newper.entity.Category;
import com.newper.entity.Company;
import com.newper.exception.MsgException;
import com.newper.mapper.CategoryMapper;
import com.newper.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;

    private final CategoryMapper categoryMapper;

    @Transactional
    public void categoryOrder(Integer[] cate_idx){
        //마지막은 드래그앤드랍 중복되는 idx들어감
        for (int i = 0; i < cate_idx.length - 1; i++) {
            Category category = categoryRepo.findById(cate_idx[i]).orElseThrow(()->new MsgException("존재하지 않는 대분류 입니다."));
            //미노출이 음수이므로 order는 0이 아닌 1부터 시작하게끔
            category.updateCategoryOrder(i+1);
        }
    }

    @Transactional
    public void categoryDelete(int CATE_IDX){
        Category category = categoryRepo.findById(CATE_IDX).orElseThrow(()->new MsgException(("존재하지 않는 카테고리입니다.")));
        categoryRepo.delete(category);
    }

    /**카테고리 등록*/
    @Transactional
    public int categoryInsert(ParamMap paramMap, MultipartFile icon, MultipartFile thumbnail){
        Category category = paramMap.mapParam(Category.class);
        System.out.println(category.getCateIdx());
        int cate_depth = Integer.parseInt(paramMap.get("CATE_DEPTH")+"");
        Integer cateOrder = categoryMapper.maxCategoryOrderByCateDepth(cate_depth);
        if(cateOrder==null){
            cateOrder=0;
        }
        cateOrder=cateOrder+1;

        category.setCateOrder(cateOrder);

        category.setCateMemo("");
        category.setCateNick("");
        List<String > list = new ArrayList<>();
        category.setCateSpec_list(list);
        category.setCateType("");

        if (paramMap.get("CATE_PARENT_IDX")!=null){
            Category parentCategory = categoryRepo.findById(Integer.parseInt(paramMap.get("CATE_PARENT_IDX")+"")).get();
            category.setParentCategory(parentCategory);
            category.setCateIdx(null);
        }

        String iconFilePath = "";
        String thumbnailFilePath = "";
        if(icon.getSize()!=0){
            iconFilePath = Common.uploadFilePath(icon, "summernote/category/icon/", AdminBucket.OPEN);
        }
        if(thumbnail.getSize()!=0){
            thumbnailFilePath = Common.uploadFilePath(thumbnail, "summernote/category/thumbnail/",AdminBucket.OPEN);
        }

        category.setCateIcon(iconFilePath);
        category.setCateThumbnail(thumbnailFilePath);
        categoryRepo.saveAndFlush(category);


        return category.getCateIdx();
    }

    /**카테고리 수정*/
    @Transactional
    public int categoryUpdate(ParamMap paramMap, MultipartFile icon, MultipartFile thumbnail){
        Category category = categoryRepo.findById(paramMap.getInt("CATE_IDX")).get();
        Category categoryParam = paramMap.mapParam(Category.class);
        categoryParam.setCateMemo(category.getCateNick());
        categoryParam.setCateSpec_list(category.getCateSpec_list());
        categoryParam.setCateType(category.getCateType());
        categoryParam.setCateNick(category.getCateNick());
        categoryParam.setCateOrder(category.getCateOrder());
        categoryParam.setCateDepth(category.getCateDepth());
        if(paramMap.get("CATE_PARENT_IDX")!=null){
            Category parentCategory = categoryRepo.findById(paramMap.getInt("CATE_PARENT_IDX")).get();
            categoryParam.setParentCategory(parentCategory);
        }


        String iconFilePath = category.getCateIcon();
        String thumbnailFilePath = category.getCateThumbnail();

        if(icon.getSize()!=0){
            iconFilePath = Common.uploadFilePath(icon, "summernote/category/icon/", AdminBucket.OPEN);
        }
        if(thumbnail.getSize()!=0){
            thumbnailFilePath = Common.uploadFilePath(thumbnail, "summernote/category/thumbnail/",AdminBucket.OPEN);
        }
        categoryParam.setCateIcon(iconFilePath);
        categoryParam.setCateThumbnail(thumbnailFilePath);
        categoryRepo.saveAndFlush(categoryParam);

        return categoryParam.getCateIdx();

    }

}

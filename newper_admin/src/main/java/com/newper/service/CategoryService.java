package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.CateType;
import com.newper.dto.ParamMap;
import com.newper.entity.Category;
import com.newper.exception.MsgException;
import com.newper.mapper.CategoryMapper;
import com.newper.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int categoryInsert(ParamMap paramMap, MultipartFile icon, MultipartFile thumbnail) {
        Category category = paramMap.mapParam(Category.class);
        List list = paramMap.getList("list");
        System.out.println("list : "+ list);
        List<Map<String, Object>> cateSpecList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map2 = new HashMap<>();
            map2.put("list", list.get(i));
            cateSpecList.add(map2);
            System.out.println("cateSpecList : " + cateSpecList);
        }
        int cate_depth = Integer.parseInt(paramMap.get("CATE_DEPTH") + "");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("CATE_DEPTH", cate_depth);
        map.put("CATE_TYPE", paramMap.get("CATE_TYPE"));
        Integer cateOrder = categoryMapper.maxCategoryOrderByCateDepth(map);

        if (cateOrder == null) {
            cateOrder = 0;
        }
        cateOrder = cateOrder + 1;

        category.setCateOrder(cateOrder);
        category.setCateMemo("");
        category.setCateNick("");
        category.setCateSpecList(category.getCateSpecList());

        if (paramMap.get("CATE_PARENT_IDX") != null) {
            Category parentCategory = categoryRepo.findById(Integer.parseInt(paramMap.get("CATE_PARENT_IDX") + "")).get();
            category.setParentCategory(parentCategory);
            category.setCateIdx(null);
        }

        String iconFilePath = "";
        String thumbnailFilePath = "";
        if (icon.getSize() != 0) {
            iconFilePath = Common.uploadFilePath(icon, "category/icon/", AdminBucket.OPEN);
        }
        if (thumbnail.getSize() != 0) {
            thumbnailFilePath = Common.uploadFilePath(thumbnail, "category/thumbnail/", AdminBucket.OPEN);
        }
        category.setCateIcon(iconFilePath);
        category.setCateThumbnail(thumbnailFilePath);
        category.setCateSpecList(cateSpecList);

        categoryRepo.saveAndFlush(category);

        return category.getCateIdx();
    }

    /**카테고리 수정*/
    @Transactional
    public int categoryUpdate(ParamMap paramMap, MultipartFile icon, MultipartFile thumbnail){
        Category category = categoryRepo.findById(paramMap.getInt("CATE_IDX")).get();

        Category categoryParam = paramMap.mapParam(Category.class);
        categoryParam.setCateMemo(category.getCateNick());
        categoryParam.setCateSpecList(category.getCateSpecList());
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
            iconFilePath = Common.uploadFilePath(icon, "category/icon/", AdminBucket.OPEN);
        }
        if(thumbnail.getSize()!=0){
            thumbnailFilePath = Common.uploadFilePath(thumbnail, "category/thumbnail/",AdminBucket.OPEN);
        }

        List list = paramMap.getList("list");
        System.out.println("list : "+ list);
        List<Map<String, Object>> cateSpecList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("list", list.get(i));
            cateSpecList.add(map);
            System.out.println("cateSpecList : " + cateSpecList);
        }

        categoryParam.setCateSpecList(cateSpecList);
        categoryParam.setCateIcon(iconFilePath);
        categoryParam.setCateThumbnail(thumbnailFilePath);
        categoryRepo.saveAndFlush(categoryParam);

        return categoryParam.getCateIdx();

    }

    /**카테고리(브랜드) 노출여부 수정*/
    @Transactional
    public String categoryDisplayUpdate(ParamMap paramMap){

        String cateIdxs[] = paramMap.get("CATE_IDXS").toString().split(",");
        for(int i=0; i<cateIdxs.length; i++){
            Category category = categoryRepo.findById(Integer.parseInt(cateIdxs[i])).get();
            categoryRepo.saveAndFlush(category);
        }

        return "변경되었습니다.";
    }

    /**카테고리(브랜드) 삭제*/
    @Transactional
    public String brandDelete(ParamMap paramMap){
        String cateIdxs[] = paramMap.get("CATE_IDXS").toString().split(",");
        for(int i=0; i<cateIdxs.length; i++){
            categoryRepo.deleteById(Integer.parseInt(cateIdxs[i]));
        }
        List<Category> list = categoryRepo.findCategoryByCateType(CateType.BRAND.name());
        for(int j=0; j<list.size(); j++){
            Category category = list.get(j);
            category.updateCategoryOrder(j+1);
        }
        return "삭제되었습니다.";
    }

    /**카테고리-고시정보템플릿 update*/
    @Transactional
    public void updateCateInfo(ParamMap paramMap) {
        Category category = categoryRepo.findById(paramMap.getInt("cateIdx")).orElseThrow(() -> new MsgException("존재하지 않는 카테고리입니다."));

        List<String> title = paramMap.getList("title");
        List<String> content = paramMap.getList("content");

        List<Map<String, Object>> infoList = new ArrayList<>();
        for (int i = 0; i < title.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            if (!StringUtils.hasText(title.get(i))) {
                throw new MsgException("미입력된 고시정보항목이 있습니다.");
            }
            if (!StringUtils.hasText(content.get(i))) {
                throw new MsgException("미입력된 고시정보내용이 있습니다.");
            }
            map.put("title", title.get(i));
            map.put("content", content.get(i));
            infoList.add(map);
        }

        category.setCateInfo(infoList);
    }
}

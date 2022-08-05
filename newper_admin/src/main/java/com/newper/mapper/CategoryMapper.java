package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    /**대분류 카테고리 전체 조회*/
    List<Map<String, Object>> selectCategoryListByParent();

    /**부모 카테고리가 있는 카테고리 CATE_PARENT_IDX 로 조회 */
    List<Map<String, Object>> selectCategoryListByChildren(Integer cate_parent_idx);

    /** 카테고리 조회 IDX없으면 전체 조회 그 외 부모 카테고리 하위 조회 */
    List<Map<String, Object>> selectCategory(Map<String, Object> param);

    /**카테고리 order 마지막 값 depth로 가져오기*/
    Integer maxCategoryOrderByCateDepth(int CATE_DEPTH);

    /**뎁스별 카테고리 조회*/
    List<Map<String,Object>> selectCategoryListByCateDepth(int CATE_DEPTH);
}

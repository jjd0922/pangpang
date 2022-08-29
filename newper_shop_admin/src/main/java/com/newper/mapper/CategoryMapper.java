package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {

    /**부모 카테고리가 있는 카테고리 CATE_PARENT_IDX 로 조회 */
    List<Map<String, Object>> selectCategoryDatatableByChildren(Integer SCATE_IDX);
    /**카테고리 대분류 로 조회 */
    List<Map<String, Object>> selectCategoryListByParent();


}

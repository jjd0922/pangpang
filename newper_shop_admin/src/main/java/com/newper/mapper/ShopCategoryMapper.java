package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopCategoryMapper {

    /**대분류 카테고리 전체 조회*/
    List<Map<String, Object>> selectShopCategoryDatatableByParent();

    /**부모 카테고리가 있는 카테고리 CATE_PARENT_IDX 로 조회 */
    List<Map<String, Object>> selectShopCategoryDatatableByChildren(Integer SCATE_IDX);

    /**shop category order 마지막 값 depth로 가져오기*/
    Integer maxShopCategoryOrderBySCateDepth(int SCATE_DEPTH);

    /**category 중분류*/
    List<Map<String, Object>> selectCategoryDatatableByTwoDetph(Map<String, Object> map);

    /**category 중분류의 전시대분류*/
    Map<String,Object> selectCategoryDetailByTwoDetph(int CATE_IDX);

}

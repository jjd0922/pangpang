package com.pangpang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
@Transactional
public interface CategoryMapper {
    @Transactional(readOnly = true)
    List<Map<String, Object>> selectCategoryList(@Param("cateIdx") String cateIdx,
                                                 @Param("cateParentIdx") String cateParentIdx,
                                                 @Param("cateType") String cateType,
                                                 @Param("cateDepth") String cateDepth,
                                                 @Param("cateName") String cateName);
}

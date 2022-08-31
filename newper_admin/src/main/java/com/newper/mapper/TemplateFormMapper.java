package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TemplateFormMapper {
    /** kakao/sms 템플릿 조회 테이블*/
    List<Map<String, Object>> selectTemplateFormDatatable(Map<String, Object> param);

    Integer countTemplateFormDatatable(Map<String, Object> param);


    /** kakao템플릿 일괄 삭제*/
    void deleteTemplate(Map<String, Object> map);

    }

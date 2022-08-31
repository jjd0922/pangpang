package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface ConsultationResultMapper {


    List<Map<String, Object>> selectConsultationResultDatatable(Map<String, Object> param);

    Integer countConsultationResultDatatable(Map<String, Object> param);


}

package com.newper.mapper;

import java.util.List;
import java.util.Map;

import com.newper.dto.ParamMap;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CompanyMapper {
    List<Map<String, Object>> selectCompanyDatatable(Map<String, Object> param);

    Integer countCompanyDatatable(Map<String, Object> param);

    List<Map<String, Object>> selectCompanyContract(Map<String, Object> param);

    long countCompanyContract(Map<String, Object> param);
}

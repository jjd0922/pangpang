package com.newper.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CompanyMapper {
    List<Map<String, Object>> selectCompany();

    Integer countCompany();

    List<Map<String, Object>> selectCompanyContract(Map<String, Object> param);

    long countCompanyContract(Map<String, Object> param);
}

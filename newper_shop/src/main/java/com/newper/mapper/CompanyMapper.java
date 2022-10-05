package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CompanyMapper {

    /**거래처구분코드 select*/
    List<Map<String, Object>> selectCompanyType();


}

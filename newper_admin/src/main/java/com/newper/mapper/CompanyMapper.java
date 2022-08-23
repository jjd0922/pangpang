package com.newper.mapper;

import java.util.List;
import java.util.Map;

import com.newper.dto.ParamMap;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CompanyMapper {

    /**거래처 조회 Datatable*/
    List<Map<String, Object>> selectCompanyDatatable(Map<String, Object> param);
    long countCompanyDatatable(Map<String, Object> param);

    /**거래처구분코드 insert*/
    int insertCompanyType(Map<String, Object> param);

    /**거래처구분코드 select*/
    List<String> selectCompanyType(Integer comIdx);

    /**거래처계약관리 조회 Datatable*/
    List<Map<String, Object>> selectCompanyContract(Map<String, Object> param);
    long countCompanyContract(Map<String, Object> param);

    List<Map<String, Object>> test();

    /**거래처 중 입점사 조회 Datatable*/
    List<Map<String, Object>> selectStoreDatatable(Map<String, Object> param);
    long countStoreDatatable(Map<String, Object> param);

    /**입점사 카테고리별 수수료 조회 Datatable*/
    List<Map<String, Object>> selectFeeDatatable(Map<String, Object> param);
    long countFeeDatatable(Map<String, Object> param);

    /**매입처 보증보험 조회 Datatable*/
    List<Map<String, Object>> selectInsuranceDatatable(Map<String, Object> param);
    long countInsuranceDatatable(Map<String, Object> param);

    /**CT_TYPE별 거래처 데이터테이블*/
    List<Map<String ,Object>> selectCompanyDatatableByCtType(Map<String ,Object> map);
    Integer countCompanyDatatableByCtType(Map<String ,Object> map);
}

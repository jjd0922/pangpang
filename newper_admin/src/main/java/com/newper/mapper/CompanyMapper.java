package com.newper.mapper;

import java.util.List;
import java.util.Map;

import com.newper.dto.ParamMap;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CompanyMapper {
    List<Map<String, Object>> selectCompanyDatatable(Map<String, Object> param);

    long countCompanyDatatable(Map<String, Object> param);

    int insertCompanyType(Map<String, Object> param);

    List<Map<String, Object>> selectCompanyType(Integer comIdx);

    List<Map<String, Object>> selectCompanyContract(Map<String, Object> param);

    long countCompanyContract(Map<String, Object> param);

    List<Map<String, Object>> test();

    List<Map<String, Object>> selectStoreDatatable(Map<String, Object> param);

    long countStoreDatatable(Map<String, Object> param);

    List<Map<String, Object>> selectFeeDatatable(Map<String, Object> param);

    long countFeeDatatable(Map<String, Object> param);

    List<Map<String, Object>> selectInsuranceDatatable(Map<String, Object> param);

    long countInsuranceDatatable(Map<String, Object> param);

    /**CT_TYPE별 거래처 데이터테이블*/
    List<Map<String ,Object>> selectCompanyDatatableByCtType(Map<String ,Object> map);
    Integer countCompanyDatatableByCtType(Map<String ,Object> map);
}

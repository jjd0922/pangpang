package com.newper.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IamportMapper {

    /** PG 리스트 전체 조회*/
    List<Map<String,Object>> selectIamportPgList();
    /** PG리스트별 사용하는 결제 수단 조회 */
    List<Map<String,Object>> selectIamportMethodMap();
    /** 결제수단 정보 조회*/
    Map<String,Object> selectIamportMethodDetail(int ipm_idx);
}

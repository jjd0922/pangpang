package com.newper.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IamportMapper {

    /** 간편결제 PG 리스트 조회*/
    List<Map<String,Object>> selectIamportPgList();
    /** 일반결제 결제 수단 조회 */
    List<Map<String,Object>> selectIamportMethodList();
    /** 결제수단 정보 조회*/
    Map<String,Object> selectIamportMethodDetail(int ipm_idx);
}

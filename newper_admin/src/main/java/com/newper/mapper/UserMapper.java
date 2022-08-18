package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserMapper {
    List<Map<String, Object>> selectUserDatatable(Map<String, Object> param);

    Integer countUserDatatable(Map<String, Object> param);

    List<Map<String, Object>> selectUserForCompany(Map<String, Object> param);

    Long countUserForCompany(Map<String, Object> param);

    List<Map<String, Object>> selectUserType(Integer uIdx);
    List<Map<String, Object>> selectUser(Map<String, Object> param);

    long countUser(Map<String, Object> param);

    List<Map<String, Object>> insertUser (Integer uIdx);
    /**로그인 체크 위해 조회*/
    Map<String, Object> selectUserLogin(@Param("id") String id,@Param("pw") String pw);

}

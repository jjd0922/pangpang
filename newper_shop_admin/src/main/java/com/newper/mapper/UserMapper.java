package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**로그인 체크 위해 조회*/
    Map<String, Object> selectUserLogin(@Param("id") String id,@Param("pw") String pw);

}

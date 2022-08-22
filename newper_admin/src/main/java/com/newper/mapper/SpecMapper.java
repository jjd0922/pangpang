package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpecMapper {

    /** specl_name으로 value목록 가져오기*/
    List<String> selectSpecListValueList(String speclName);
}

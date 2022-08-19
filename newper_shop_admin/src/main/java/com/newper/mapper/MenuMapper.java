package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {

    /** authIdx로 접근 가능한 submenu url 하나 가져오기*/
    String selectSubMenuUrlByAuth(String authIdx);
}

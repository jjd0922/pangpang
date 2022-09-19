package com.newper.repository;

import com.newper.entity.SpecList;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface SpecListRepo extends JpaRepository<SpecList, Integer> {
    public SpecList findSpecListBySpeclNameAndSpeclValue(String speclName, String speclValue);

}

package com.newper.repository;

import com.newper.entity.CalculateSetting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculateSettingRepo extends JpaRepository <CalculateSetting,Integer> {
    @EntityGraph(attributePaths = {"company"})
    public CalculateSetting findCalculateSettingBycsIdx(Integer csIdx);
}

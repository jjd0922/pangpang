package com.newper.repository;

import com.newper.entity.CalculateAdjust;
import com.newper.entity.CalculateGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalculateAdjustRepo extends JpaRepository<CalculateAdjust, Integer> {

    List<CalculateAdjust> findByCalculateGroup(CalculateGroup calculateGroup);
}

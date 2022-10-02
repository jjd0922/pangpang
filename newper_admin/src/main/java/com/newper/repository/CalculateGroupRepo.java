package com.newper.repository;

import com.newper.entity.CalculateGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculateGroupRepo extends JpaRepository<CalculateGroup, Integer> {

    @EntityGraph(attributePaths = {"company", "company.user"})
    CalculateGroup findByCcgIdx(int ccgIdx);
}

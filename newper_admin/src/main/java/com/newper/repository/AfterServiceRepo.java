package com.newper.repository;

import com.newper.entity.AfterService;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AfterServiceRepo extends JpaRepository<AfterService, Long> {
    @EntityGraph(attributePaths = {"deliveryNum", "deliveryNum2"})
    public AfterService findByAsIdx(long asIdx);

}

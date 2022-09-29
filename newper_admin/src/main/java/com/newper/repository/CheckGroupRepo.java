package com.newper.repository;

import com.newper.entity.CheckGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckGroupRepo extends JpaRepository<CheckGroup, Integer> {
    @EntityGraph(attributePaths = {"company", "company.user", "user", "user2"})
    public CheckGroup findCheckGroupByCgIdx(Integer cgIdx);
}

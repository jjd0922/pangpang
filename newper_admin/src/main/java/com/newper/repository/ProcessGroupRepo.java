package com.newper.repository;

import com.newper.entity.CheckGroup;
import com.newper.entity.ProcessGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessGroupRepo extends JpaRepository<ProcessGroup, Integer> {

    @EntityGraph(attributePaths = {"company", "company.user", "user", "user2"})
    public ProcessGroup findByPgIdx(int pgIdx);
}

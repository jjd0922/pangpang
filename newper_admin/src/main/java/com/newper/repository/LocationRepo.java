package com.newper.repository;

import com.newper.entity.Location;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<Location, Integer> {

    @EntityGraph(attributePaths = {"warehouse", "user"})
    public Location findLocationByLocIdx(Integer locIdx);


}

package com.newper.repository;

import com.newper.entity.EventGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventGroupRepo extends JpaRepository<EventGroup,Long> {

    @EntityGraph(attributePaths = {"shop"})
    EventGroup findShopByEgIdx(Long egIdx);
}

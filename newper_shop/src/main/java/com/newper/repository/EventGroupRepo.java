package com.newper.repository;

import com.newper.entity.EventGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventGroupRepo extends JpaRepository<EventGroup,Long> {

    @EntityGraph(attributePaths = {"shop","eventCategoryList"})
    List<EventGroup> findEventGroupByShop_shopIdxAndEgStateTrue(@Param("shopIdx") Integer shopIdx);
}

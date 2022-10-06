package com.newper.repository;

import com.newper.constant.EgMenu;
import com.newper.entity.EventGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventGroupRepo extends JpaRepository<EventGroup,Long> {

    @EntityGraph(attributePaths = {"shop","eventCategoryList"})
    List<EventGroup> findEventGroupByShop_shopIdxAndEgStateTrue(@Param("shopIdx") Integer shopIdx);
    List<EventGroup> findEventGroupByShop_shopIdxAndEgStateTrueAndEgMenu(@Param("shopIdx") Integer shopIdx, @Param("egMenu") EgMenu egMenu);

    @EntityGraph(attributePaths = {"shop","eventCategoryList"})
    EventGroup findByegIdx(Long egIdx);
}

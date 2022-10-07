package com.newper.repository;

import com.newper.constant.EgMenu;
import com.newper.entity.EventGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventGroupRepo extends JpaRepository<EventGroup,Long> {

    /** 진행중인 이벤트 전체 조회*/
    @EntityGraph(attributePaths = {"shop","eventCategoryList"})
    List<EventGroup> findEventGroupByShop_shopIdxAndEgStateTrueAndEgCloseDateAfter(@Param("shopIdx") Integer shopIdx, LocalDate date);
    /** 진행중인 이벤트 메뉴별 조회*/
    @EntityGraph(attributePaths = {"shop","eventCategoryList"})
    List<EventGroup> findEventGroupByShop_shopIdxAndEgStateTrueAndEgMenuAndEgCloseDateAfter(@Param("shopIdx") Integer shopIdx, @Param("egMenu") EgMenu egMenu, LocalDate date);

    /** 종료된 이벤트 전체 조회*/
    @EntityGraph(attributePaths = {"shop","eventCategoryList"})
    List<EventGroup> findEventGroupByShop_shopIdxAndEgStateTrueAndEgCloseDateBefore(@Param("shopIdx") Integer shopIdx, LocalDate date);

    /** 종료된 이벤트 메뉴별 조회*/
    @EntityGraph(attributePaths = {"shop","eventCategoryList"})
    List<EventGroup> findEventGroupByShop_shopIdxAndEgStateTrueAndEgMenuAndEgCloseDateBefore(@Param("shopIdx") Integer shopIdx, @Param("egMenu") EgMenu egMenu, LocalDate date);

    /** 이벤트 상세*/
    @EntityGraph(attributePaths = {"shop","eventCategoryList"})
    EventGroup findByegIdx(Long egIdx);
}

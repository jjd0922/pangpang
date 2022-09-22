package com.newper.repository;

import com.newper.entity.Notice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepo extends JpaRepository<Notice, Long> {

    @EntityGraph(attributePaths = {"shop"})
    Notice findShopByNtIdx(Long ntIdx);
}

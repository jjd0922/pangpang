package com.newper.repository;

import com.newper.entity.MainSection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainSectionRepo extends JpaRepository<MainSection, Long> {
    /** 메인섹션 리스트 조회 - 분양몰 idx, 노출순서*/
    @EntityGraph(attributePaths = {"mainSectionBanners"})
    List<MainSection> findByShop_shopIdxOrderByMsOrder(Integer shopIdx);
}

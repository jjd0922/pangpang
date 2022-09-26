package com.newper.repository;

import com.newper.entity.MainSectionBanner;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainSectionBannerRepo extends JpaRepository<MainSectionBanner, Long> {

    @EntityGraph(attributePaths = {"mainSection"})
    List<MainSectionBanner> findByMainSection_msIdxOrderByMsbnOrder(Long msIdx);
}

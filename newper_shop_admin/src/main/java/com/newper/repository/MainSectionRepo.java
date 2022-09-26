package com.newper.repository;

import com.newper.entity.MainSection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainSectionRepo extends JpaRepository<MainSection,Long> {

//    @EntityGraph(attributePaths = {"shop", "mainSectionBanners"})
//    MainSection findMainSectionBymsIdx(Long msIdx);

//    @EntityGraph(attributePaths = {"shop", "mainSectionSps","mainSectionBanners"})
//    MainSection findMainSectionBymsIdxAndMsType(Long msIdx, String msType);

    @EntityGraph(attributePaths = {"shop"})
    List<MainSection> findByShop_shopIdx(Integer shopIdx);
}

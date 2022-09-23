package com.newper.repository;

import com.newper.entity.MainSection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainSectionRepo extends JpaRepository<MainSection,Long> {

    @EntityGraph(attributePaths = {"shop"})
    MainSection findMainSectionBymsIdx(Long msIdx);
}

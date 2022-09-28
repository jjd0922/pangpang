package com.newper.repository;

import com.newper.entity.MainSectionSp;
import com.newper.entity.MainSectionSpEmbedded;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainSectionSpRepo extends JpaRepository<MainSectionSp, MainSectionSpEmbedded> {
}

package com.newper.repository;

import com.newper.entity.GiftGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface GiftGroupRepo extends JpaRepository<GiftGroup, Long> {
}

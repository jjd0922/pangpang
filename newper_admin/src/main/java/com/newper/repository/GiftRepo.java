package com.newper.repository;

import com.newper.entity.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface GiftRepo extends JpaRepository<Gift, Long> {
}

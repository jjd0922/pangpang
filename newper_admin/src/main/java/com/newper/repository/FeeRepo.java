package com.newper.repository;

import com.newper.entity.Fee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepo extends JpaRepository<Fee, Integer> {
}

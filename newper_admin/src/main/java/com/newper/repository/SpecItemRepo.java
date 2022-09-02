package com.newper.repository;

import com.newper.entity.Spec;
import com.newper.entity.SpecItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecItemRepo extends JpaRepository<SpecItem, Integer> {
}

package com.newper.repository;

import com.newper.entity.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateRepo extends JpaRepository<Estimate, Integer> {

}

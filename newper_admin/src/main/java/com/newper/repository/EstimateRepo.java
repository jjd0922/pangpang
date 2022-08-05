package com.newper.repository;

import com.newper.entity.Company;
import com.newper.entity.Estimate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateRepo extends JpaRepository<Estimate, Integer> {
    @EntityGraph(attributePaths = {"company"})
    public Estimate findEstimateByPeIdx(Integer peIdx);

}

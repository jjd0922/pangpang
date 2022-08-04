package com.newper.repository;

import com.newper.entity.Insurance;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepo extends JpaRepository<Insurance, Integer> {

    @EntityGraph(attributePaths = {"company"})
    public Insurance findInsuranceByCiIdx(Integer ciIdx);

}

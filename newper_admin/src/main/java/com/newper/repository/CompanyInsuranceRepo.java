package com.newper.repository;

import com.newper.entity.CompanyInsurance;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CompanyInsuranceRepo extends JpaRepository<CompanyInsurance, Integer> {

    @EntityGraph(attributePaths = {"company"})
    public CompanyInsurance findInsuranceByCiIdx(Integer ciIdx);

}

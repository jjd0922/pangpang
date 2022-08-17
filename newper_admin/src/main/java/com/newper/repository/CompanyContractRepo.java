package com.newper.repository;

import com.newper.entity.CompanyContract;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyContractRepo extends JpaRepository<CompanyContract, Integer> {

    @EntityGraph(attributePaths = {"company", "companyEmployee", "user"})
    public CompanyContract findContractByccIdx(Integer ccIdx);
}

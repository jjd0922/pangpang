package com.newper.repository;

import com.newper.entity.Company;
import com.newper.entity.Contract;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepo extends JpaRepository<Contract, Integer> {

    @EntityGraph(attributePaths = {"company", "companyEmployee", "user"})
    public Contract findContractByccIdx(Integer ccIdx);
}

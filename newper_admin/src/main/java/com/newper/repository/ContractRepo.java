package com.newper.repository;

import com.newper.entity.Contract;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepo extends JpaRepository<Contract, Integer> {

    @EntityGraph(attributePaths = {"company", "companyEmployee"})
    public Contract findContractByccIdx(Integer ccIdx);
}

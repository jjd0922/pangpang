package com.newper.repository;

import com.newper.entity.Po;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoRepo extends JpaRepository<Po, Integer> {

    @EntityGraph(attributePaths = {"company", "company_sell", "warehouse", "companyContract", "hiworks"})
    public Po findPoByPoIdx(Integer poIdx);
}

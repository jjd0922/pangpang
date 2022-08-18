package com.newper.repository;

import com.newper.entity.Company;
import com.newper.entity.Po;
import com.newper.entity.SpecList;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoRepo extends JpaRepository<Po, Integer> {

    @EntityGraph(attributePaths = {"company", "company_sell", "warehouse", "companyContract", "hiworks"})
    public Po findPoByPoIdx(Integer poIdx);
}

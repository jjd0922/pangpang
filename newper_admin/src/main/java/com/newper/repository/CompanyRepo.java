package com.newper.repository;

import com.newper.entity.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer> {

    @EntityGraph(attributePaths = {"companyEmployee"})
    public Company findCompanyByComIdx(Integer comIdx);

}

package com.newper.repository;

import com.newper.entity.Category;
import com.newper.entity.Company;
import com.newper.entity.CompanyFee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CompanyFeeRepo extends JpaRepository<CompanyFee, Integer> {

    @EntityGraph(attributePaths = {"company", "category"})
    public CompanyFee findFeeBycfIdx(Integer cfIdx);

    @EntityGraph(attributePaths = {"company", "category"})
    public CompanyFee findFeeByCompanyAndCategoryAndCfState(Company company, Category category, char cfState);

}

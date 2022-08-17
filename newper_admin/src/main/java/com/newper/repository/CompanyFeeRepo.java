package com.newper.repository;

import com.newper.entity.CompanyFee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyFeeRepo extends JpaRepository<CompanyFee, Integer> {

    @EntityGraph(attributePaths = {"company", "category"})
    public CompanyFee findFeeBycfIdx(Integer cfIdx);

}

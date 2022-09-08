package com.newper.repository;

import com.newper.entity.CouponGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponGroupRepo extends JpaRepository<CouponGroup, Long> {

    @EntityGraph(attributePaths = {"shop"})
    public CouponGroup findBycpgIdx(Long cpgIdx);

}
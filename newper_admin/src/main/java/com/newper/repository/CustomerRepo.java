package com.newper.repository;

import com.newper.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @EntityGraph(attributePaths = {"shop"})
    public Customer findBycuIdx(Long cuIdx);
}
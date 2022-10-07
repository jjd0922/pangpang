package com.newper.repository;

import com.newper.entity.Customer;
import com.newper.entity.Review;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"customer", "shopProduct", "orders"})
    List<Review> findAllByCustomer(Customer customer, Sort sort);
}
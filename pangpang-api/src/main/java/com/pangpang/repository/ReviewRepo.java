package com.pangpang.repository;

import com.pangpang.entity.Board;
import com.pangpang.entity.Customer;
import com.pangpang.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review,Long> {
    Review findByBoardAndCustomer(Board board,Customer customer);
}

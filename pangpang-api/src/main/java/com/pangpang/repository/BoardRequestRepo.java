package com.pangpang.repository;

import com.pangpang.entity.Board;
import com.pangpang.entity.BoardRequest;
import com.pangpang.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRequestRepo extends JpaRepository<BoardRequest,Long> {

    BoardRequest findByBoardAndCustomer(Board board, Customer customer);
}

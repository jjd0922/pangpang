package com.pangpang.repository;

import com.pangpang.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepo extends JpaRepository<Board,Long> {
}

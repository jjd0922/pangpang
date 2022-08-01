package com.newper.repository;

import com.newper.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthRepo extends JpaRepository<Menu, Integer> {
}

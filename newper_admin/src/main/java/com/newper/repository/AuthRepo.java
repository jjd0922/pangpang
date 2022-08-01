package com.newper.repository;

import com.newper.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepo extends JpaRepository<Auth, Integer> {
}

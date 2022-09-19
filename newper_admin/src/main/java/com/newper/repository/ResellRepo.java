package com.newper.repository;

import com.newper.entity.Auth;
import com.newper.entity.Resell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResellRepo extends JpaRepository<Resell, Integer> {

}

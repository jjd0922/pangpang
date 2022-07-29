package com.newper.repository;

import com.newper.entity.CompanyEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyEmployeeRepo extends JpaRepository<CompanyEmployee, Integer> {
}

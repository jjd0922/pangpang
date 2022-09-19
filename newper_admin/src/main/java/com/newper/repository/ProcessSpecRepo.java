package com.newper.repository;

import com.newper.entity.Category;
import com.newper.entity.ProcessSpec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessSpecRepo extends JpaRepository<ProcessSpec, Integer> {

}

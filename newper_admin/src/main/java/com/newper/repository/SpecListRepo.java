package com.newper.repository;

import com.newper.entity.Fee;
import com.newper.entity.SpecList;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecListRepo extends JpaRepository<SpecList, Integer> {
    public List<SpecList> findSpecListBySpeclName(String speclName);
}

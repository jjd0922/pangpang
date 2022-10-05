package com.newper.repository;

import com.newper.entity.Sabang;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SabangRepo extends JpaRepository<Sabang, Integer> {

    @EntityGraph(attributePaths = {"orderGs"})
    public Sabang findBySaMallProductId(String saMallProductId);

}

package com.newper.repository;

import com.newper.entity.Company;
import com.newper.entity.Resell;
import com.newper.entity.ResellGoods;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResellGoodsRepo extends JpaRepository<ResellGoods, Integer> {
    @EntityGraph(attributePaths = {"goods"})
    public ResellGoods findByRgIdx(Integer rgIdx);
}

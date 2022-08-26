package com.newper.repository;

import com.newper.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public interface GoodsRepo extends JpaRepository<Goods, Long> {



}

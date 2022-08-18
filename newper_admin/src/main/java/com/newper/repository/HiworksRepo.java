package com.newper.repository;

import com.newper.entity.Hiworks;
import com.newper.entity.PoProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiworksRepo extends JpaRepository<Hiworks, Integer> {

}

package com.newper.repository;

import com.newper.entity.InGroup;
import com.newper.entity.Po;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InGroupRepo extends JpaRepository<InGroup, Integer> {

    InGroup findByPo(Po po);

    InGroup findInGroupByPoPoIdx (Integer poIdx);
}

package com.newper.repository;

import com.newper.entity.Spec;
import com.newper.entity.SpecList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecRepo extends JpaRepository<Spec, Integer> {
    public Spec findSpecBySpecConfirm(String specConfirm);
}

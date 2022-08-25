package com.newper.repository;

import com.newper.entity.Warehouse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepo extends JpaRepository<Warehouse,Integer> {

    @EntityGraph(attributePaths = {"company", "company.companyEmployee"})
    public Warehouse findWarehouseByWhIdx(Integer whIdx);

}

package com.newper.repository;

import com.newper.entity.Domain;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepo extends JpaRepository<Domain, Integer> {

    @EntityGraph(attributePaths = {"shop"})
    Domain findByDomUrl(String domUrl);
}

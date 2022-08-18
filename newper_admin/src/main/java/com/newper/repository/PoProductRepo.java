package com.newper.repository;

import com.newper.entity.EstimateProduct;
import com.newper.entity.Po;
import com.newper.entity.PoProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoProductRepo extends JpaRepository<PoProduct, Integer> {
    @EntityGraph(attributePaths = {"product"})
    public List<PoProduct> findPoProductByPo_PoIdx(Integer poIdx);
}

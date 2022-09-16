package com.newper.repository;

import com.newper.entity.PoProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface PoProductRepo extends JpaRepository<PoProduct, Integer> {
    @EntityGraph(attributePaths = {"product"})
    public List<PoProduct> findPoProductByPo_PoIdx(Integer poIdx);

    @EntityGraph(attributePaths = {"spec"})
    public PoProduct findTopByPo_poIdxAndProduct_pIdx(Integer poIdx, Integer pIdx);

}

package com.newper.repository;

import com.newper.entity.InGroup;
import com.newper.entity.InProduct;
import com.newper.entity.Po;
import com.newper.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InProductRepo extends JpaRepository<InProduct, Integer> {
    InProduct findByProductAndInGroup(Product product, InGroup inGroup);
}

package com.newper.repository;

import com.newper.entity.Customer;
import com.newper.entity.QnaSp;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaSpRepo extends JpaRepository<QnaSp, Long> {

    /** 마이페이지 > 상품문의 조회 */
    @EntityGraph(attributePaths = {"customer", "shopProduct", "qnaAnswer"})
    List<QnaSp> findAllByCustomerOrderByQspIdxDesc(Customer customer);
}

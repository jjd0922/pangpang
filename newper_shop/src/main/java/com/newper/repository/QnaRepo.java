package com.newper.repository;

import com.newper.entity.Customer;
import com.newper.entity.Qna;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepo extends JpaRepository<Qna, Long> {

    /** 고객별 1:1문의 리스트 */
    @EntityGraph(attributePaths = {"orderGsGroup.orders", "customer", "qnaAnswer"})
    List<Qna> findAllByCustomerOrderByQnaIdxDesc(Customer customer);
}

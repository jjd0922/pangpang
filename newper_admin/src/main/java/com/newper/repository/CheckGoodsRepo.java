package com.newper.repository;

import com.newper.entity.CheckGoods;
import com.newper.entity.CheckGroup;
import com.newper.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckGoodsRepo extends JpaRepository<CheckGoods, Integer> {

    /** 자산과 검수그룹으로 자산 - 검수 관계 조회 */
    CheckGoods findByGoodsAndCheckGroup(Goods goods, CheckGroup checkGroup);
}

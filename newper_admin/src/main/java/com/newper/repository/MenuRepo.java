package com.newper.repository;

import com.newper.entity.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepo extends JpaRepository<Menu, Integer> {

    /** 메뉴 리스트 가져오기*/
    @Query(value = "select distinct m from Menu m join fetch m.subMenuList order by m.menuOrder")
    List<Menu> findMenuALl();
}

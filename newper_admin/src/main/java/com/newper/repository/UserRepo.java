package com.newper.repository;

import com.newper.constant.UState;
import com.newper.entity.Category;
import com.newper.entity.Company;
import com.newper.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    @EntityGraph(attributePaths = {"company"})
    public User findUserByuIdx(Integer uIdx);

    List<User> findUserByuState(String uState);

}

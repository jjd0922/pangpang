package com.newper.repository;

import com.newper.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {
    @EntityGraph(attributePaths = {"company"})
    public User findUserByuIdx(Integer uIdx);

    List<User> findUserByuState(String uState);


    User findUserByuId(@Param("U_ID") String uId);
}

package com.newper.repository;

import com.newper.entity.Company;
import com.newper.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    @EntityGraph(attributePaths = {"company"})
    public User findUserByuIdx(Integer uIdx);

}

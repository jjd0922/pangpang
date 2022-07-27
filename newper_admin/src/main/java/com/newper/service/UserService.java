package com.newper.service;


import com.newper.entity.User;
import com.newper.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {


    @Autowired
    private final UserRepo userRepo;


    public void userCreate(User user) {
        userRepo.save(user);

    }
}

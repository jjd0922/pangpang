package com.newper.service;


import com.newper.dto.ParamMap;
import com.newper.entity.Auth;
import com.newper.entity.User;
import com.newper.exception.MsgException;
import com.newper.repository.AuthRepo;
import com.newper.repository.CompanyRepo;
import com.newper.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class UserService {

    private final UserRepo userRepo;
    private final AuthRepo authRepo;
    private final CompanyRepo companyRepo;

    @Transactional
    public Integer saveUser(ParamMap paramMap) {
        System.out.println("paramMap = " + paramMap);
//        Address address = paramMap.mapParam(Address.class);
//        Company company = paramMap.mapParam(Company.class);
        User user =  paramMap.mapParam(User.class);
        System.out.println("userIdx : " + user.getUIdx());
        Auth auth = authRepo.findById(1).orElseThrow(()-> new MsgException("권한이 없습니다."));
        user.setAuth(auth);
        System.out.println(auth.getAuthIdx());

        System.out.println("here1");
//        user.setAddress(address);

        System.out.println("here2");
        user.setUIdx(1);
        User saveUser = userRepo.save(user);
        System.out.println("here3");

        return saveUser.getUIdx();
    }


}


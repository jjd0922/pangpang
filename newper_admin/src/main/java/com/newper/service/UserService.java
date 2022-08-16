package com.newper.service;


import com.newper.constant.ComType;
import com.newper.constant.CtType;
import com.newper.constant.UType;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.mapper.UserMapper;
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
    private UserMapper userMapper;


    /**사용자신규 등록**/
    @Transactional
    public Integer saveUser(ParamMap paramMap) {
        Address address = paramMap.mapParam(Address.class);

        Company company = paramMap.mapParam(Company.class);

        User user = paramMap.mapParam(User.class);

        try{
            int u_auth_idx = paramMap.getInt("U_AUTH_IDX");
        }catch (NumberFormatException nfe){
            throw new MsgException("권한을 선택해주세요");
        }

        Auth auth =paramMap.mapParam(Auth.class);

        if(paramMap.getString("CT_TYPE_LIST").contains(CtType.MAIN.name())){
                user.setUType(UType.INSIDE);
        }else{ user.setUType(UType.OUTSIDE);

        }
        user.setAuth(auth);

        user.setAddress(address);

        user.setCompany(company);

        userRepo.save(user);

        return user.getUIdx();
    }
    /**
     * 사용자등록 수정 처리
     */
    @Transactional
    public Integer updateUser(ParamMap paramMap) {

        User user=paramMap.mapParam(User.class);
        Auth auth = paramMap.mapParam(Auth.class);
        Address address = paramMap.mapParam(Address.class);
        Company company = paramMap.mapParam(Company.class);
        System.out.println("userIdx : " + user.getUIdx());

        user.setCompany(company);
        user.setAuth(auth);
        user.setAddress(address);

        userRepo.save(user);
        return user.getUIdx();
    }
    }



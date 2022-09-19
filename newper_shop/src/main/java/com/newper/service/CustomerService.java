package com.newper.service;


import com.newper.component.ShopSession;
import com.newper.entity.Customer;
import com.newper.exception.MsgException;
import com.newper.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    @Autowired
    private ShopSession shopSession;

    private final CustomerRepo customerRepo;


    /** 로그인 처리 */
    @Transactional
    public void login(String id, String pw) {

        Customer customer = customerRepo.findByCuId(id);
        if (customer == null) {
            throw new MsgException("존재하지 않는 id입니다");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(pw.getBytes("UTF-8"));
            String pw_sha2 = String.format("%0128x", new BigInteger(1, digest.digest()));

            if(!customer.getCuPw().equals(pw_sha2)){
                throw new MsgException("잘못된 비밀번호 입니다");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        shopSession.login(customer);

        customer.login();


    }

}

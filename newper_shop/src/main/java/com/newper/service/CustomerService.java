package com.newper.service;


import com.newper.component.Common;
import com.newper.component.ShopSession;
import com.newper.constant.CuState;
import com.newper.dto.ParamMap;
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

    /** 비밇번호 확인 */
    public String pwdCheck(String pw) {
        Customer customer = customerRepo.findByCuId(shopSession.getId());

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(pw.getBytes("UTF-8"));
            String pw_sha2 = String.format("%0128x", new BigInteger(1, digest.digest()));

            if(!customer.getCuPw().equals(pw_sha2)){
                return "잘못된 비밀번호 입니다";
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        shopSession.setPwdCheck(true);
        return "Y";
    }
    @Transactional
    public void join (ParamMap paramMap) {
        boolean isExistCustomer = customerRepo.existsByCuId(paramMap.getString("cuId"));
        if (isExistCustomer) {
            throw new MsgException("이미 사용중인 아이디입니다.");
        }

        Customer customer = paramMap.mapParam(Customer.class);
        String cuPhone = paramMap.getString("phone1")+"-"+paramMap.getString("phone2")+"-"+paramMap.getString("phone3");
        String cuEmail = paramMap.getString("email1")+"@"+paramMap.getString("email2");
        customer.join(cuPhone, cuEmail, paramMap.getMap());
        customerRepo.save(customer);
    }

}

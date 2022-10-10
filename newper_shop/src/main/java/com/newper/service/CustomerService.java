package com.newper.service;


import com.newper.component.ShopSession;
import com.newper.constant.CuGender;
import com.newper.constant.SaCode;
import com.newper.dto.ParamMap;
import com.newper.entity.AesEncrypt;
import com.newper.entity.Cart;
import com.newper.entity.Customer;
import com.newper.entity.SelfAuth;
import com.newper.exception.MsgException;
import com.newper.repository.CartRepo;
import com.newper.repository.CustomerRepo;
import com.newper.repository.SelfAuthRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    @Autowired
    private ShopSession shopSession;

    private final CustomerRepo customerRepo;
    private final SelfAuthRepo selfAuthRepo;
    private final ShopRepo shopRepo;
    private final CartRepo cartRepo;


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
    public boolean pwdCheck(String pw) {
        if (!StringUtils.hasText(shopSession.getId())) {
            throw new MsgException("로그인이 필요한 페이지입니다.");
        }
        Customer customer = customerRepo.findByCuId(shopSession.getId());

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

        shopSession.setPwdCheck(true);
        return true;
    }

    /**회원가입*/
    @Transactional
    public Customer join (ParamMap paramMap) {
        // 아이디 중복 확인
        boolean isIdExist = customerRepo.existsByCuId(paramMap.getString("cuId"));
        if (isIdExist == true) {
            throw new MsgException("이미 사용중인 아이디입니다.");
        }
        // 본인인증 응답값 가져오고 그 응답이 이미 사용되었는지 확인
        AesEncrypt ae = new AesEncrypt();
        SelfAuth selfAuth = selfAuthRepo.findLockBySaIdx(Long.parseLong(ae.decryptRandom(paramMap.getString("saIdx"))));

        if (selfAuth.isSaUsed()) {
            throw new MsgException("잘못된 접근입니다.");
        }
        //본인 인증 성공적으로 된건지
        if( selfAuth.getSaCode() != SaCode.SUCCESS){
            throw new MsgException("잘못된 접근입니다.");
        }

        // customer entity set
        Customer customer = paramMap.mapParam(Customer.class);
        customer.join(selfAuth.getSaRes());
        customer.setShop(shopRepo.getReferenceById(shopSession.getShopIdx()));
        if (StringUtils.hasText(paramMap.getString("cuRecommender"))) {
            boolean isRecommenderExist = customerRepo.existsByCuId(paramMap.getString("cuRecommender"));
            if (!isRecommenderExist) {
                throw new MsgException("추천인 아이디를 확인해주세요.");
            } else {
                customer.setCuRecommender(paramMap.getString("cuRecommender"));
            }
        }
        Customer savedCu = customerRepo.save(customer);
        selfAuth.setSaUsed(true);
        return savedCu;
    }

    /** 네이버 간편로그인 > 회원가입 */
    @Transactional
    public void naverJoin(ParamMap paramMap) {
        Customer customer = Customer.builder()
                .cuId(paramMap.getString("id"))
                .cuName(paramMap.getString("name"))
                .cuGender(CuGender.valueOf(paramMap.getString("gender")))
                .cuBirth(LocalDate.parse(paramMap.getString("birthyear") + "-" + paramMap.getString("birthday")))
                .cuPhone(paramMap.getString("mobile"))
                .build();
        customerRepo.save(customer);
    }

    /** 비밀번호 찾기 > 비밀번호 재설정 */
    @Transactional
    public void resetPw(ParamMap paramMap) {
        Customer customer = customerRepo.findById(paramMap.getLong("cuIdx")).orElseThrow(() -> new MsgException("회원정보를 찾을 수 없습니다."));
        customer.setCuPw(paramMap.getString("cuPw"));
        customer.setCuPwChange(LocalDate.now());
    }

    /** 마이페이지 > 회원정보수정 > 비밀번호 변경*/
    @Transactional
    public void changePw(ParamMap paramMap) {
        if (!StringUtils.hasText(shopSession.getId())) {
            throw new MsgException("로그인이 필요한 페이지입니다.");
        }
        Customer customer = customerRepo.findByCuId(shopSession.getId());

        if (!StringUtils.hasText(paramMap.getString("cuPwOld"))) {
            throw new MsgException("기존 비밀번호를 입력해 주세요.");
        }
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(paramMap.getString("cuPwOld").getBytes("UTF-8"));
            String pw_sha2 = String.format("%0128x", new BigInteger(1, digest.digest()));

            if(!customer.getCuPw().equals(pw_sha2)){
                throw new MsgException("기존 비밀번호가 일치하지 않습니다.");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        customer.setCuPw(paramMap.getString("cuPw"));
        customer.setCuPwChange(LocalDate.now());
    }
    /** 장바구니 insert*/
    @Transactional
    public void insertCart(ParamMap paramMap){

        Customer customer = customerRepo.getReferenceById(shopSession.getIdx());

        for (String key : paramMap.keySet()) {
            if (key.indexOf("spo") == 0) {

                Cart cart = cartRepo.findByCustomerAndCartSpo(customer, key).orElseGet(() -> {
                    return Cart.builder()
                            .customer(customer)
                            .cartSpo(key)
                            .build();
                });
                cart.addCartCnt(paramMap.getInt(key));
                cartRepo.save(cart);
            }
        }

    }
}

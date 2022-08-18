package com.newper.service;


import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.mapper.CompanyMapper;
import com.newper.mapper.UserMapper;
import com.newper.repository.AuthRepo;
import com.newper.repository.CompanyRepo;
import com.newper.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class UserService {

    private final UserRepo userRepo;
    private final AuthRepo authRepo;
    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;


    /**사용자신규 등록**/
    @Transactional
    public Integer saveUser(ParamMap paramMap) {
        User user = paramMap.mapParam(User.class);
        try{
            int u_auth_idx = paramMap.getInt("U_AUTH_IDX");
            user.setAuth(authRepo.getReferenceById(u_auth_idx));
        }catch (NumberFormatException nfe){
            throw new MsgException("권한을 선택해주세요");
        }
        try{
            int u_com_idx = paramMap.getInt("U_COM_IDX");
            Company company = companyRepo.getReferenceById(u_com_idx);
            user.setCompany(company);

            List<String> ctTypes = companyMapper.selectCompanyType(u_com_idx);
            if (ctTypes.contains(CtType.MAIN.name())) {
                user.setUType(UType.INSIDE);
            }else{
                user.setUType(UType.OUTSIDE);
            }
        }catch (NumberFormatException nfe){
            throw new MsgException("소속 업체를 선택해주세요");
        }

        Address address = paramMap.mapParam(Address.class);
        user.setAddress(address);

        //생년월일

        String u_birth = paramMap.getString("U_BIRTH");
        LocalDate uBirth = LocalDate.parse(u_birth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        user.setUBirth(uBirth);

        try{
            userRepo.save(user);
        }catch (DataIntegrityViolationException de){
            throw new MsgException("중복된 ID입니다");
        }

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

    /** 비밀번호 초기화. 변경된 비밀번호 return*/
    @Transactional
    public String userUpdatePwd(int uIdx) {
        User user = userRepo.findById(uIdx).orElseThrow(()-> new MsgException("존재하지 않는 회원입니다."));
        user.setRandomPassword(8);

        return user.getUPassword();
    }


    /** 회원상태 일괄변경*/
    @Transactional
    public void userUpdateState(Integer uIdx, String uState) {
        User user = userRepo.findById(uIdx).orElseThrow(()-> new MsgException("존재하지 않는 회원입니다."));
        user.setUState(uState);
    }


    /**계정상태변경 삭제*/
    @Transactional
    public String userDelete(ParamMap paramMap){
        String uIdxs[] = paramMap.get("U_IDXS").toString().split(",");
        for(int i=0; i<uIdxs.length; i++){
            userRepo.deleteById(Integer.parseInt(uIdxs[i]));
        }
        List<User> list = userRepo.findUserByuState(UState.RETIRE.name());
        for(int j=0; j<list.size(); j++){
            User user = list.get(j);
           user.updateUser(j+1);
        }
        return "삭제되었습니다.";
    }

}



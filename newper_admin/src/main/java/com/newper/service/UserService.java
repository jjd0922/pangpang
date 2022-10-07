package com.newper.service;


import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.entity.common.AddressEmb;
import com.newper.exception.MsgException;
import com.newper.mapper.CompanyMapper;
import com.newper.mapper.UserMapper;
import com.newper.repository.AuthRepo;
import com.newper.repository.CompanyRepo;
import com.newper.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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


    /**
     * 사용자신규 등록
     **/
    @Transactional
    public Integer saveUser(ParamMap paramMap) {
        User user = paramMap.mapParam(User.class);

        try {
            String u_name = paramMap.getString("U_NAME");
            user.setUName(u_name);
        } catch (NumberFormatException nfe) {
            throw new MsgException("이름을 입력해주세요");
        }
        try {
            String u_phone = paramMap.getString("U_PHONE");
            user.setUPhone(u_phone);
        } catch (NumberFormatException nfe) {
            throw new MsgException("휴대폰번호를 입력해주세요");
        }
        try {
            String u_state = paramMap.getString("U_STATE");
            user.setUState(u_state);
        } catch (NumberFormatException nfe) {
            throw new MsgException("상태를 체크해주세요");
        }
        try {
            int u_auth_idx = paramMap.getInt("U_AUTH_IDX");
            user.setAuth(authRepo.getReferenceById(u_auth_idx));
        } catch (NumberFormatException nfe) {
            throw new MsgException("권한을 선택해주세요");
        }
        try {
            int u_com_idx = paramMap.getInt("U_COM_IDX");
            Company company = companyRepo.getReferenceById(u_com_idx);
            user.setCompany(company);
            System.out.println(u_com_idx);
            List<String> ctTypes = companyMapper.selectCompanyType(u_com_idx);
            if (ctTypes.contains(CtType.MAIN.name())) {
                user.setUType(UType.INSIDE);
            } else {
                user.setUType(UType.OUTSIDE);
            }
            System.out.println(user.getUType());
        } catch (NumberFormatException nfe) {
            throw new MsgException("소속 업체를 선택해주세요");
        }


        AddressEmb address = paramMap.mapParam(AddressEmb.class);
        user.setAddress(address);

        String u_birth = paramMap.getString("U_BIRTH");
        if (!paramMap.getString("U_BIRTH").equals("")) {
            LocalDate uBirth = LocalDate.parse(u_birth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            user.setUBirth(uBirth);
        } else if (paramMap.getString("U_BIRTH").equals("")) {
            paramMap.put("U_BIRTH", null);
        } else {
            paramMap.put("U_BIRTH", null);
        }

        try {
            String u_id = paramMap.getString("U_ID");
            user.setUId(u_id);
        } catch (NumberFormatException nfe) {
            throw new MsgException("로그인ID를 입력해주세요");
        }
        try {
            String u_password = paramMap.getString("U_PASSWORD");
            user.setUPassword(u_password);
        } catch (NumberFormatException nfe) {
            throw new MsgException("패스워드를 입력해주세요");
        }
        try {

        } catch (DataIntegrityViolationException de) {
            throw new MsgException("중복된 ID입니다");
        }

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
        AddressEmb address = paramMap.mapParam(AddressEmb.class);
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
    public void userUpdateState(Integer uIdx, String U_STATE) {
        User user = userRepo.findById(uIdx).orElseThrow(()-> new MsgException("존재하지 않는 회원입니다."));
        user.setUState(U_STATE);
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



package com.pangpang.controller.rest;

import com.pangpang.dto.ReturnMap;
import com.pangpang.entity.Customer;
import com.pangpang.exception.MsgException;
import com.pangpang.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RequestMapping(value = "/api/customer/")
@RestController
@RequiredArgsConstructor
public class ApiCustomerController {

    private final CustomerRepo customerRepo;

    @PostMapping("register.ajax")
    public ReturnMap customerRegister(@RequestBody Map<String,Object> map) {
        ReturnMap rm = new ReturnMap();
        if(map.isEmpty()){
            throw new MsgException("데이터 없음");
        }
        Customer cu = customerRepo.findByCuId(map.get("CU_ID").toString());
        Customer cu2 = customerRepo.findByCuNickname(map.get("CU_NICKNAME").toString());
        if(cu != null){
            throw new MsgException("이미 회원가입한 아이디 입니다.");
        }
        if(cu2 != null){
            throw new MsgException("해당 닉네임은 사용 불가입니다.");
        }
        LocalDate now = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        Customer customer = Customer.builder()
                .cuId(map.get("CU_ID").toString())
                .cuName(map.get("CU_NAME").toString())
                .cuNickname(map.get("CU_NICKNAME").toString())
                .cuMail(map.get("CU_MAIL").toString())
                .cuPhone(map.get("CU_PHONE").toString())
           //     .cuCi(map.get("CU_CI").toString())
                .cuBirth(map.get("CU_BIRTH").toString())
                .cuGender(map.get("CU_GENDER").toString())
                .cuMemo(map.get("CU_MEMO").toString())
                .cuState("NORMAL")
                .cuJoinDate(now)
                .cuJoinTime(nowTime)
                .build();
        customerRepo.save(customer);
        rm.put("cu_idx",customer.getCuIdx());
        rm.setMessage("가입완료");
       return rm;
    }
    @GetMapping("nickName-check.ajax")
    public ReturnMap customerNickNameCheck(@RequestParam(value = "CU_NICKNAME",required = false) String cuNickname) {
       ReturnMap rm = new ReturnMap();
       if(cuNickname.equals("")){
           throw new MsgException("입력값이 없습니다.");
       }
       boolean chk =  customerRepo.findByCuNickname(cuNickname) == null ? true : false;
       rm.put("result",chk);
       return rm;
    }
    @PostMapping("login.ajax")
    public ReturnMap customerLogin(@RequestBody Map<String,Object> map) {
        ReturnMap rm = new ReturnMap();
        if(map.isEmpty()){
            throw new MsgException("데이터 없음");
        }
        Customer cu = customerRepo.findByCuId(map.get("CU_ID").toString());
        if(cu == null){
            throw new MsgException("가입되지 않은 회원입니다.");
        }
        rm.put("cu_idx",cu.getCuIdx());
        return rm;
    }
    @GetMapping("info.ajax")
    public ReturnMap customerInfo(@RequestParam(value = "CU_IDX",required = false) String cuIdx) {
        ReturnMap rm = new ReturnMap();
        Customer cu = customerRepo.findById(Long.parseLong(cuIdx)).get();
        rm.put("list",cu);
        return rm;
    }
}

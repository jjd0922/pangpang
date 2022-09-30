package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Customer;
import com.newper.mapper.CustomerMapper;
import com.newper.repository.CustomerRepo;
import com.newper.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/")
public class CustomerRestController {

    private final CustomerRepo customerRepo;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping("join.ajax")
    public ReturnMap join(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        Customer customer = customerService.join(paramMap);
        customerService.login(customer.getCuId(), customer.getCuPw());
        rm.setLocation("/customer/joinComplete");
        return rm;
    }

    /** 아이디 중복체크*/
    @PostMapping("checkId.ajax")
    public ReturnMap checkId(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        Customer customer = customerRepo.findByCuId(paramMap.getString("id"));
        if (customer == null) {
            rm.setMessage("사용 가능한 ID입니다.");
            rm.put("result", true);
        } else {
            rm.setMessage("이미 사용중인 ID입니다.");
            rm.put("result", false);
        }
        return rm;
    }

    /** 아이디 찾기 */
    @PostMapping("findId.ajax")
    public Map<String,Object> findId(ParamMap paramMap) {
        Map<String, Object> map = customerMapper.selectCustomerByCuCi(paramMap.getMap());
        return map;
    }

}

package com.newper.controller.rest;

import com.newper.component.NiceApi;
import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Customer;
import com.newper.repository.CustomerRepo;
import com.newper.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/")
public class CustomerRestController {

    @Autowired
    private ShopSession shopSession;

    private final CustomerRepo customerRepo;
    private final CustomerService customerService;
    private final NiceApi niceApi;

    @PostMapping("join.ajax")
    public ReturnMap join(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        customerService.join(paramMap);
        String resCode = "0000";
        if(resCode.equals("0000")){
            rm.setLocation("/joinComplete");
            rm.setMessage("회원가입 완료!");
        }else{
            rm.setMessage("잠시 후 다시 시도해주세요.");
        }
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

    /**회원가입 페이지에 nice 본인인증 요청데이터 return*/
    @PostMapping(value="auth/response.ajax")
    public ReturnMap authResponse(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        rm.put("map", paramMap.getMap());
        return rm;
    }


}

package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Customer;
import com.newper.exception.MsgException;
import com.newper.mapper.CustomerMapper;
import com.newper.repository.CustomerRepo;
import com.newper.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final CustomerMapper customerMapper;

    /**회원가입처리*/
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

    /** 비밀번호 확인 */
    @PostMapping("pwdCheck.ajax")
    public ReturnMap pwdCheck(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();

        String pw = paramMap.getString("pw");
        boolean result = customerService.pwdCheck(pw);
        rm.put("result", result);
        return rm;
    }

    /** 아이디/비밀번호 찾기 */
    @PostMapping("find/{type}.ajax")
    public Map<String,Object> findId(@PathVariable("type") String type, ParamMap paramMap) {
        Map<String, Object> map = customerMapper.selectCustomerByCuCi(paramMap.getMap());
        if (type.equals("pw")) {
            if (!paramMap.getString("cuId").equals(map.get("CU_ID").toString())) {
                throw new MsgException("아이디가 일치하지 않습니다.");
            }
        }
        return map;
    }

    /** 마이페이지 > 회원정보수정 > 비밀번호 변경*/
    @PostMapping("changePw.ajax")
    public ReturnMap changePw(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        customerService.changePw(paramMap);
        rm.setMessage("비밀번호 변경이 완료되었습니다.");
        return rm;
    }
    /** 장바구니 담기*/
    @PostMapping("cart.ajax")
    public ReturnMap cart(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();

        Long cuIdx = shopSession.getIdx();
        if (cuIdx == null) {
            rm.put("cart", false);
        }else{
            rm.put("cart", true);
            customerService.insertCart(paramMap);

        }

        return rm;
    }

}

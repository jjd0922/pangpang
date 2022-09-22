package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.exception.MsgException;
import com.newper.service.CustomerService;
import com.newper.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MainRestController {

    private final ShopService shopService;
    private final CustomerService customerService;

    /** 쇼핑몰 정보 조회*/
    @PostConstruct
    @Scheduled(cron = "0 0 * * * *")
    @GetMapping("refreshShop.ajax")
    public void setShopComp(){
        shopService.setShopComp();
    }
    /** 로그인 */
    @PostMapping("login.ajax")
    public ReturnMap login(String id, String pw){
        ReturnMap rm = new ReturnMap();

        try {
            customerService.login(id, pw);
            rm.put("result", "200");
        }catch (MsgException me){
            rm.put("result", me.getMessage());
        }

        return rm;
    }
    /** 로그아웃*/
    @GetMapping("logout.ajax")
    public void logout(HttpServletRequest request){
        request.getSession().invalidate();
    }

    /** 회원가입 (임시) */
    @PostMapping("join.ajax")
    public ReturnMap join(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        String resCode = "0000";
        if(resCode.equals("0000")){
            rm.setLocation("/joinComplete");
            rm.setMessage("회원가입 완료!");
        }else{
            rm.setMessage("잠시 후 다시 시도해주세요.");
        }
        return rm;
    }

    /** 비밇번호 확인 */
    @PostMapping("pwdCheck.ajax")
    public ReturnMap pwdCheck(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();

        String pw = paramMap.getString("pw");
        String result = customerService.pwdCheck(pw);

        rm.setMessage(result);
        return rm;
    }
}

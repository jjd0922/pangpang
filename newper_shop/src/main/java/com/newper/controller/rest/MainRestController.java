package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.dto.ReturnMap;
import com.newper.exception.MsgException;
import com.newper.service.CustomerService;
import com.newper.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MainRestController {


    @Autowired
    private ShopSession shopSession;
    private final ShopService shopService;
    private final CustomerService customerService;
//    private final ShopComp shopComp;

    @PostConstruct
    public void postCon(){
        shopService.setShopComp("localhost");
    }

    /** set shopComp info. 분양몰 정보 세팅 */
    @GetMapping("refreshShop")
    public void refreshShop(HttpServletRequest request){
        System.out.println(shopSession.getIdx());

        String domain = request.getServerName();
        shopService.setShopComp(domain);

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
}

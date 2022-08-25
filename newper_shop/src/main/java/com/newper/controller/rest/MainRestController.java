package com.newper.controller.rest;

import com.newper.component.ShopComp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MainRestController {


    private final ShopService shopService;
//    private final ShopComp shopComp;

    /** set shopComp info. 분양몰 정보 세팅 */
    @GetMapping("refreshShop")
    public void refreshShop(HttpServletRequest request){
        String domain = request.getServerName();
        shopService.setShopComp(domain);
    }
}

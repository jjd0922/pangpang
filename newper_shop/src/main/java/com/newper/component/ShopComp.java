package com.newper.component;

import com.newper.entity.HeaderMenu;
import com.newper.entity.Shop;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 분양몰 정보 저장하는 class*/
@Component
@Getter
@Setter
public class ShopComp {

    /** key domain, value shop info*/
    private Map<String, Shop> shopMap = new HashMap<>();

    /** 도메인으로 header메뉴 가져오기*/
    public List<HeaderMenu> getHeaderMenu(String domain){
        return shopMap.get(domain).getHeaderMenulist();
    }

}

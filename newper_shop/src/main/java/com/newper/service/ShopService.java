package com.newper.service;


import com.newper.component.ShopComp;
import com.newper.entity.Domain;
import com.newper.entity.Shop;
import com.newper.mapper.ShopMapper;
import com.newper.repository.DomainRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopComp shopComp;
    private final ShopRepo shopRepo;
    private final DomainRepo domainRepo;
    private final ShopMapper shopMapper;

    /** shop 정보 가져오기 */
    public void setShopComp() {

        List<Domain> domainList = domainRepo.findWithShopBy();
        for (Domain domain : domainList) {
            Shop shop = domain.getShop();
            shopComp.getShopMap().put(domain.getDomUrl(), shop);

            //select sql
            //N+1 (shop갯수만큼 조회됨) vs shop 중복 조회 효율
            shop.getHeaderMenulist().size();

            Map<String,Object> shopDesignMap = shopMapper.selectShopDesignJson(shop.getShopIdx());
            shopComp.setShopDesignClass(shopDesignMap);

        }
    }

}
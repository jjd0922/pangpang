package com.newper.controller.rest;

import com.newper.component.ShopComp;
import com.newper.entity.Domain;
import com.newper.entity.Shop;
import com.newper.repository.DomainRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopComp shopComp;
    private final ShopRepo shopRepo;
    private final DomainRepo domainRepo;

    /** shop 정보 가져오기 */
    public void setShopComp(String domain) {
        Domain dom = domainRepo.findByDomUrl(domain);

        Shop shop = dom.getShop();
        shopComp.getShopMap().put(domain, shop);

        //select sql
        shop.getHeaderMenulist().size();

    }

}

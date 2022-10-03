package com.newper.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.newper.component.ShopComp;
import com.newper.entity.Domain;
import com.newper.entity.MainSection;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.mapper.CategoryMapper;
import com.newper.mapper.ShopMapper;
import com.newper.repository.DomainRepo;
import com.newper.repository.MainSectionRepo;
import com.newper.repository.ShopCategoryRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
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
    private final CategoryMapper categoryMapper;
    private final ShopCategoryRepo shopCategoryRepo;
    private final MainSectionRepo mainSectionRepo;


    /** shop 정보 가져오기 */
    public void setShopComp() {

        List<Domain> domainList = domainRepo.findWithShopBy();
        for (Domain domain : domainList) {
            Shop shop = domain.getShop();
            shopComp.getShopMap().put(domain.getDomUrl(), shop);

            //select sql
            //N+1 (shop갯수만큼 조회됨) vs shop 중복 조회 효율
            shop.getHeaderMenulist().size();
            shop.getHeaderOrderList().size();

            // 분양몰 디자인 정보
            Map<String,Object> shopDesignMap = shopMapper.selectShopDesignJson(shop.getShopIdx());
            shopComp.setShopDesignClass(shopDesignMap);
            shopComp.setShopColorMap(shopDesignMap);

//            List<ShopCategory> shopCategoryList = shopCategoryRepo.findAll();
//            List<Map<String,Object>> allCateList = new ArrayList<>();
//            for(int i = 0; i< shopCategoryList.size();i++){
//                Integer scateIdx = shopCategoryList.get(i).getScateIdx();
//                Map<String,Object> map = new HashMap<>();
//                map.put("scateIdx", scateIdx);
//                map.put("shopIdx", shop.getShopIdx());
//                List<Map<String, Object>> cateList= categoryMapper.selectAllCategoryByShopProduct(map);
//                for(int k=0;k<cateList.size();k++){
//                    allCateList.add(cateList.get(k));
//                }
//            }

            // 카테고리 정보
            shopComp.setShopCategoryList(shopCategoryRepo.findAllByAndScateOrderGreaterThanOrderByScateOrderAsc(0));

            // 메인섹션리스트
            List<MainSection> mainSectionList = mainSectionRepo.findByShop_shopIdxAndMsOrderGreaterThanOrderByMsOrderAsc(shop.getShopIdx(), 0);
            List<Map<String,Object>> jsonList = new ArrayList<>();
            for(int i=0;i<mainSectionList.size();i++){
                for(int k=0;k<mainSectionList.get(i).getMainSectionBanners().size();k++){
                    mainSectionList.get(i).getMainSectionBanners().get(k);
                }
                for(int k=0;k<mainSectionList.get(i).getMainSectionSps().size();k++){
                    mainSectionList.get(i).getMainSectionSps().get(k).getShopProduct().getSpName();
                }
                if(mainSectionList.get(i).getMsJson() !=null && !mainSectionList.get(i).getMsJson().equals("")){
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mainSectionList.get(i).getMsJson();
                    try {
                        Map<String,Object> map = mapper.readValue(json,Map.class);
                        map.put("msIdx", String.valueOf(mainSectionList.get(i).getMsIdx()));
                        jsonList.add(map);
                    }catch (IOException e){
                        System.out.println(e);
                        throw new MsgException("잠시 후 다시 시도해주세요.");
                    }
                }
            }


            shopComp.setMainSectionList(mainSectionList);
            shopComp.setMsJsonList(jsonList);


        }
    }

}
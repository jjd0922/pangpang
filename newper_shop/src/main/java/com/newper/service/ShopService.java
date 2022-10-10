package com.newper.service;


import com.newper.component.ShopComp;
import com.newper.entity.Domain;
import com.newper.entity.MainSection;
import com.newper.entity.Shop;
import com.newper.mapper.CategoryMapper;
import com.newper.mapper.ShopMapper;
import com.newper.repository.DomainRepo;
import com.newper.repository.MainSectionRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopComp shopComp;
    private final ShopRepo shopRepo;
    private final DomainRepo domainRepo;
    private final ShopMapper shopMapper;
    private final MainSectionRepo mainSectionRepo;
    private final CategoryMapper categoryMapper;


    /** shop 정보 가져오기 */
    public void setShopComp() {

        List<Domain> domainList = domainRepo.findWithShopBy();

        // 카테고리 정보
        //전시대분류 전체 LIST
        List<Map<String, Object>> scateList = categoryMapper.selectAllShopCategory();
        shopComp.setShopCategoryList(scateList);
        //중분류 전체 조회
        // key : 전시 idx / value : 중분류
        List<Map<String, Object>> middleCateList = categoryMapper.selectAllMiddleCategory();
        Map<Object, List<Map<String, Object>>> csc_scate_idx = middleCateList.stream().collect(Collectors.groupingBy(map -> map.get("CSC_SCATE_IDX")));
        shopComp.setMiddleCategoryList(csc_scate_idx);
        //소분류
        // key : 중분류 idx / value : 소분류
        List<Map<String, Object>> smallCateList = categoryMapper.selectAllSmallCategory();
        Map<Object, List<Map<String, Object>>> middle_cate_idx = smallCateList.stream().collect(Collectors.groupingBy(map -> map.get("CATE_PARENT_IDX")));
        shopComp.setSmallCategoryList(middle_cate_idx);

        for (Domain domain : domainList) {
            Shop shop = domain.getShop();
            shopComp.getShopMap().put(domain.getDomUrl(), shop);

            //select sql
            //N+1 (shop갯수만큼 조회됨) vs shop 중복 조회 효율
            shop.getHeaderMenulist().size();
            shop.getHeaderOrderList().size();
            shop.getFloatingBarList().size();

            // 분양몰 디자인 정보
            Map<String,Object> shopDesignMap = shopMapper.selectShopDesignJson(shop.getShopIdx());
            shopComp.setShopDesignClass(shopDesignMap);
            shopComp.setShopColorMap(shopDesignMap);



            // 메인섹션리스트
            List<MainSection> mainSectionList = mainSectionRepo.findByShop_shopIdxAndMsOrderGreaterThanOrderByMsOrderAsc(shop.getShopIdx(), 0);
            for(int i=0;i<mainSectionList.size();i++){
                for(int k=0;k<mainSectionList.get(i).getMainSectionBanners().size();k++){
                    mainSectionList.get(i).getMainSectionBanners().get(k);
                }
                for(int k=0;k<mainSectionList.get(i).getMainSectionSps().size();k++){
                    mainSectionList.get(i).getMainSectionSps().get(k).getShopProduct().getSpName();
                }
            }
            shopComp.setMainSectionList(mainSectionList);

            // 분양몰 별 카테고리 리스트
            List<Map<String,Object>> spCateList = categoryMapper.selectAllCategoryByProduct(shop.getShopIdx());
            shopComp.setShopProductCategoryList(spCateList);
        }


    }

}
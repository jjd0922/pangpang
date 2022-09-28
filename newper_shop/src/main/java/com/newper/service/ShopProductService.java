package com.newper.service;

import com.newper.dto.OrdersSpoDTO;
import com.newper.dto.ParamMap;
import com.newper.entity.ShopProduct;
import com.newper.entity.ShopProductAdd;
import com.newper.entity.ShopProductOption;
import com.newper.exception.MsgException;
import com.newper.repository.ShopProductAddRepo;
import com.newper.repository.ShopProductOptionRepo;
import com.newper.repository.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShopProductService {

    private final ShopProductRepo shopProductRepo;
    private final ShopProductAddRepo shopProductAddRepo;
    private final ShopProductOptionRepo shopProductOptionRepo;

    /** 주문상세에서 보여질 분양몰 상품 정보 조회. sessionShopIdx != null인 경우 분양몰 일치하는지도 체크 */
    @Transactional(readOnly = true)
    public ShopProduct selectShopProductInfo(long spIdx, Integer sessionShopIdx){

        ShopProduct shopProduct = shopProductRepo.findInfosBySpIdx(spIdx);
        if (shopProduct == null) {
            throw new MsgException("존재하지 않는 상품입니다");
        }

        if( sessionShopIdx != null && shopProduct.getShop().getShopIdx().intValue() != sessionShopIdx ){
            //해당 분양몰에서 판매하는 상품인지 check
            throw new MsgException("존재하지 않는 상품입니다");
        }


        if( !shopProduct.isShow()){
            throw new MsgException("현재 볼 수 없는 상품입니다");
        }

        return shopProduct;
    }
    /** 주문 결제 페이지에서 보여질 상품 정보 조회. key shopProduct : : value (spo dto List (옵션 별)) */
    @Transactional(readOnly = true)
    public Map<ShopProduct, List<OrdersSpoDTO>> selectOrdersInfo(ParamMap paramMap){
        Map<ShopProduct, List<OrdersSpoDTO>> dtoMap = new HashMap<>();

        //dtoMap 세팅
        for (String key : paramMap.keySet()) {
            //spo{idx} or 결합상품은 spo{idx}_{idx}
            if (key.indexOf("spo") == 0) {
                String[] spos = key.substring(3).split("_");

                ShopProduct shopProduct = null;
                OrdersSpoDTO dto = OrdersSpoDTO.builder()
                        .val(key)
                        .cnt(paramMap.getInt(key))
                        .build();

                for (String spo_str : spos) {
                    ShopProductOption spo = shopProductOptionRepo.findSpaBySpoIdx(Long.parseLong(spo_str));
                    dto.addShopProductOption(spo);

                    if (shopProduct == null) {
                        shopProduct = spo.getShopProductAdd().getShopProduct();
                    }
                }

                List<OrdersSpoDTO> dtoList = dtoMap.get(shopProduct);
                if (dtoList == null) {
                    dtoList = new ArrayList<>();
                    dtoMap.put(shopProduct, dtoList);
                }
                dtoList.add(dto);
            }
        }

        //dtoMap에 필수 옵션들이 포함 되어 있는지 확인
        //sp 단위로 확인
        for (ShopProduct shopProduct : dtoMap.keySet()) {
            List<ShopProductAdd> shopProductAddList = shopProduct.getShopProductAddList();

            //옵션 자리별 있어야하는 . key spaIdx_depth, value spo Idx list
            Map<String,Set<Long>> spaReqMap = new HashMap<>();
            //reqSpoList 세팅
            for (ShopProductAdd shopProductAdd : shopProductAddList) {
                if(shopProductAdd.isSpaRequired()){
                    //key spoDepth, value spo idx
                    //spo depth별 필요한 spo idx 목록 세팅
                    for (ShopProductOption spo : shopProductAdd.getShopProductOptionList()) {
                        String spaReqMap_key = shopProductAdd.getSpaIdx() + "_" + spo.getSpoDepth();
                        Set<Long> spoIdxs = spaReqMap.get(spaReqMap_key);
                        if (spoIdxs == null) {
                            spoIdxs = new HashSet<>();
                            spaReqMap.put(spaReqMap_key, spoIdxs);
                        }
                        spoIdxs.add(spo.getSpoIdx());
                    }
                }
            }

            //필수 옵션 있는지 체크
            boolean spCheck = false;
            //결제하는 옵션 반복
            spoCheck : for (OrdersSpoDTO ordersSpoDTO : dtoMap.get(shopProduct)) {
                //옵션 중 하나라도 필수값 옵션 조건 만족하는지 체크
                for (Set<Long> spoIdxs : spaReqMap.values()) {
                    if(!ordersSpoDTO.hasSpo(spoIdxs)){
                        continue spoCheck;
                    }
                }
                //continue로 가지 않고 모든 필수 옵션 가지고 있는 경우
                spCheck = true;
            }

            if(!spCheck){
                throw new MsgException( shopProduct.getSpName() +" / 필수 상품이 누락되었습니다");
            }
        }

        return dtoMap;
    }
}

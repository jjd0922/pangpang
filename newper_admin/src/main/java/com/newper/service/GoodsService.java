package com.newper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GoodsService {

    /** 자산 등록. 상품코드와 바코드만 먼저 등록. 입고검수가 되어야 입고확정스펙 나옴 */
    @Transactional
    public void insertGoods(int pp_idx, String barcode) {

        System.out.println(pp_idx);
        System.out.println(barcode);


    }
}

package com.newper.service;

import com.newper.entity.Goods;
import com.newper.entity.Po;
import com.newper.entity.Product;
import com.newper.mapper.PoMapper;
import com.newper.repository.GoodsRepo;
import com.newper.repository.PoRepo;
import com.newper.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GoodsService {

    private final ProductRepo productRepo;

    private final GoodsRepo goodsRepo;

    private final PoRepo poRepo;

    private  final PoMapper poMapper;


    /** 자산 등록. 상품코드와 바코드만 먼저 등록. 입고검수가 되어야 입고확정스펙 나옴 */
    @Transactional
    public void insertGoods(int p_idx, int po_idx, String barcode) {

        Po po = poRepo.getReferenceById(po_idx);
        Product product = productRepo.getReferenceById(p_idx);

        Goods goods= Goods.builder()
                .gBarcode(barcode)
                .po(po)
                .product(product)
                .build();

        Goods save = goodsRepo.save(goods);




        poMapper.updategoods(po_idx,p_idx);


    }
}

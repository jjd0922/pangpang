package com.newper.service;

import com.newper.constant.GgtType;
import com.newper.entity.Goods;
import com.newper.entity.GoodsGroupTemp;
import com.newper.entity.Po;
import com.newper.entity.Product;
import com.newper.exception.MsgException;
import com.newper.mapper.PoMapper;
import com.newper.repository.GoodsGroupTempRepo;
import com.newper.repository.GoodsRepo;
import com.newper.repository.PoRepo;
import com.newper.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoodsService {

    private final ProductRepo productRepo;

    private final GoodsRepo goodsRepo;

    private final PoRepo poRepo;

    private  final PoMapper poMapper;
    private  final GoodsGroupTempRepo goodsGroupTempRepo;


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
    /**입고등록팝업 바코드 삭제 */
    @Transactional
    public void barcodeDelete(long g_idx){
        //조건 체크 GOODS.java 에 생성 함


        Goods goods = goodsRepo.findById(g_idx).orElseThrow(()->new MsgException(("존재하지 않는 자산코드입니다.")));
        goodsRepo.delete(goods);

        goodsRepo.flush();

        poMapper.updategoods(goods.getPo().getPoIdx(), goods.getProduct().getPIdx());
    }
    /**입고검수 임시 그룹 생성 및 바코드 추가.*/
    @Transactional
    public void insertGoodsTemp(String idx, String[] gIdxs){

        //임시그룹 생성
        if (idx == null) {
            GoodsGroupTemp goodsGroupTemp = GoodsGroupTemp.builder()
                    .ggtType(GgtType.IN_CHECK)
                    .build();
            goodsGroupTempRepo.save(goodsGroupTemp);
            idx = goodsGroupTemp.getGgtIdx().toString();
        }

        System.out.println("idx============");
        System.out.println(idx);
        //임시그룹에 바코드 추가


    }
}

package com.newper.service;

import com.newper.constant.GRank;
import com.newper.constant.GState;
import com.newper.constant.GgtType;
import com.newper.constant.PType1;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.PoMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoodsService {

    private final ProductRepo productRepo;

    private final GoodsRepo goodsRepo;

    private final PoRepo poRepo;

    private final PoMapper poMapper;
    private final GoodsGroupTempRepo goodsGroupTempRepo;
    private final GoodsMapper goodsMapper;
    private final PoReceivedRepo poReceivedRepo;
    private final PoProductRepo poProductRepo;


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

        // 자산체번시 품목구분1 정산구분(PRODUCT.P_TYPE1)이 정상품일경우 (NORMAL)
        if (product.getPType1().equals(PType1.NORMAL)) {
           // 자동매핑
            PoProduct poProduct = poProductRepo.findTopByPo_poIdxAndProduct_pIdx(po_idx, p_idx);
            PoReceived poReceived = poMapper.selectPoReceivedByPoIdxAndPpIdx(po_idx, poProduct.getPpIdx());
            int count = 0;
            if (poReceived != null) { count = poReceived.getPorCount(); }

            poReceived = PoReceived
                    .builder()
                    .po(po)
                    .poProduct(poProduct)
                    .porProfitTarget(poProduct.getPpProfitTarget())
                    .porMemo(poProduct.getPpMemo())
                    .porSellPrice(poProduct.getPpSellPrice())
                    .porCost(poProduct.getPpCost())
                    .porOption(poProduct.getPpOption())
                    .porCount(count)
                    .build();

            poReceivedRepo.save(poReceived);

            // 입고SPEC(예정), 입고SPEC(확정), 판매SPEC(예정), 판매SPEC(확정) 전부 같음 (가공SPEC없음), 옵션
            // GOODS.G_STATE값 입고검수 (CHECK)
            goods = Goods
                    .builder()
                    .gState(GState.CHECK)
                    .inSpec(poProduct.getSpec())
                    .sellSpec(poProduct.getSpec())
//                    .stockSpec(poProduct.getSpec())
                    .gOption(poProduct.getPpOption())
                    .gRank(GRank.N1)
                    .gBarcode(barcode)
                    .gAjBarcode(poProduct.getPpMemo())
                    .po(po)
                    .product(product)
                    .poReceived(poReceived)
                    .build();

        }
        Goods save = goodsRepo.save(goods);

        poMapper.updategoods(po_idx, p_idx);
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
    public String insertGoodsTemp(String idx, String[] gIdxs){

        //임시그룹 생성
        if (idx == null) {
            GoodsGroupTemp goodsGroupTemp = GoodsGroupTemp.builder()
                    .ggtType(GgtType.IN_CHECK)
                    .build();
            goodsGroupTempRepo.save(goodsGroupTemp);
            idx = goodsGroupTemp.getGgtIdx().toString();
        }
        //임시그룹에 바코드 추가
        goodsMapper.insertGoodsTemp(idx, gIdxs);

        return idx;
    }

    /** 입고검수 임시 그룹 삭제 **/
    @Transactional
    public void deleteGGTGoods(ParamMap paramMap) {
        goodsMapper.deleteGoodsGroupTempByGIdxAndGgtIdx(paramMap.getMap());
    }

    /** 영업검수에서 자산 반품요청 & 반품반려 처리 */
    @Transactional
    public String goodsStateUpdateByCheck(ParamMap paramMap) {
        String[] gIdx = paramMap.getString("gIdxs").split(",");
        GState gState = GState.valueOf(paramMap.getString("gState"));
        String msg = "자산상태를 " + gState.getOption() + " 로 변경하였습니다.";

        for (int i = 0; i < gIdx.length; i++) {
            Goods goods = goodsRepo.findById(Long.parseLong(gIdx[i])).get();
            // 반품필요 & 입고검수 상태의 자산을 반품요청으로 상태값 변경
            if (gState.equals(GState.CANCEL_REQ)) {
                if (goods.getGState().equals(GState.CANCEL_NEED) || goods.getGState().equals(GState.CHECK)) {
                    goods.setGState(gState);
                    goodsRepo.save(goods);
                } else {
                    throw new MsgException("자산의 상태가 반품필요 & 입고검수인 자산만 반품요청할 수 있습니다.");
                }
                msg = "반품신청 완료";
            // 반품필요 자산을 입고검수 상태로 변경
            } else if (gState.equals(GState.CHECK)) {
                if (goods.getGState().equals(GState.CANCEL_NEED)) {
                    goods.setGState(gState);
                    goodsRepo.save(goods);
                } else {
                    throw new MsgException("자산의 상태가 반품필요인 자산만 반품반려할 수 있습니다.");
                }
                msg = "반품반려 완료";
            }
        }

        return msg;
    }
}

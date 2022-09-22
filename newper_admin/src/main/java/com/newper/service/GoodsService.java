package com.newper.service;

import com.newper.component.Common;
import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.PoMapper;
import com.newper.mapper.SpecMapper;
import com.newper.repository.*;
import com.newper.util.SpecFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final SpecMapper specMapper;
    private final SpecListRepo specListRepo;
    private final SpecRepo specRepo;
    private final ProcessNeedRepo processNeedRepo;
    private final InGroupRepo inGroupRepo;

    /** 자산 등록. 발주코드, 상품코드, 바코드만 먼저등록 입고검수가 되어야 입고확정스펙 나옴 */
    @Transactional
    public void insertGoods(int p_idx, int po_idx, String barcode) {
        Po po = poRepo.getReferenceById(po_idx);
        Product product = productRepo.getReferenceById(p_idx);

        InGroup inGroup = inGroupRepo.findByPo(po);

        if (inGroup.getIgState().equals(IgState.DONE)) {
            throw new MsgException("해당 발주건은 입고 완료되었습니다.");
        }

        Goods goods= Goods.builder()
                .gBarcode(barcode)
                .po(po)
                .product(product)
                .build();

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
    public String insertGoodsTemp(String idx, String[] gIdxs, GgtType ggtType){
        //임시그룹 생성
        if (idx == null) {
            GoodsGroupTemp goodsGroupTemp = GoodsGroupTemp.builder()
                    .ggtType(ggtType)
                    .build();
            goodsGroupTempRepo.save(goodsGroupTemp);
            idx = goodsGroupTemp.getGgtIdx().toString();
        }

        // 해당 요청이 입고검수일경우 해당자산의 상태값이 RECEIVED 입고 상태일때만
        if (ggtType.equals(GgtType.IN_CHECK)) {
            for (int i = 0; i < gIdxs.length; i++) {
                Goods goods = goodsRepo.findById(Long.parseLong(gIdxs[i])).get();
                if (!goods.getGState().equals(GState.RECEIVED)) {
                    throw new MsgException(goods.getGBarcode() + "는 이미 입고검수한 자산입니다.");
                }
            }
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

    /** 영업검수 자산-상품 관계 체크 */
    public List<Map<String, Object>> checkGoodsProduct(ParamMap paramMap) {
        paramMap.put("gIdxs", paramMap.getString("gIdxs").split(","));
        List<Map<String, Object>> goods = goodsMapper.goodsProductCheck(paramMap.getMap());
        if (goods.size() != 1) {
            throw new MsgException("같은 상품의 자산만 선택해 주세요");
        }

        return goods;
    }

    public List checkGoodsReceived(ParamMap paramMap) {
        paramMap.put("gIdxs", paramMap.getString("gIdxs").split(","));
        List<Map<String, Object>> goods = goodsMapper.checkGoodsReceived(paramMap.getMap());
        if (goods.size() != 1) {
            throw new MsgException("같은 입고그룹의 자산만 선택해 주세요");
        }

        return goods;
    }

    /** 영업검수 확정 */
    public void checkGoods(ParamMap paramMap) {
        String[] gIdxs = paramMap.getString("gidxs").split(",");

        List<String> specName = paramMap.getList("specName");
        List<String> specValue = paramMap.getList("spec");

        SpecFinder specFinder = new SpecFinder(specMapper, specListRepo, specRepo);
        Spec spec = specFinder.findSpec(specName, specValue);

        String gMemo = paramMap.getString("gMemo");
        GRank gRank = GRank.valueOf(paramMap.getString("gRank"));
        String gVendor = paramMap.getString("gVendor");

        PoReceived poReceived = poReceivedRepo.findById(paramMap.getLong("porIdx")).get();

        for (int i = 0; i < gIdxs.length; i++) {
            Goods goods = goodsRepo.findById(Long.parseLong(gIdxs[i])).get();
            goods.setSellSpec(spec);
            goods.setGRank(gRank);
            goods.setGMemo(gMemo);
            goods.setGVendor(gVendor);

            // 공정여부중 하나라도 Y가 있을경우 자산 상태값 Y 아니면 재고인계요청
            List<ProcessNeed> processNeed = processNeedRepo.findByGoods_gIdxAndPnType(goods.getGIdx(), PnProcess.Y);
            if (processNeed.size() != 0) {
                goods.setGState(GState.PROCESS);
            } else {
            /*    goods.setGState(GState.STOCK_REQ);*/
            }

            goodsRepo.save(goods);
        }
    }

    /** 자산 반품 가능한지 체크 */
    public void goodsResellCheck(ParamMap paramMap) {
        String[] gIdx = paramMap.getString("gIdx").split(",");
        paramMap.put("gIdxList", gIdx);

        List<Map<String, Object>> goodsListCompany = goodsMapper.selectGoodsGroupByPO_COMPANY(paramMap.getMap());
        if (goodsListCompany.size() != 1) {
            throw new MsgException("같은 매입처 자산을 선택해 주세요");
        }

        paramMap.put("gState", "CANCEL_REQ");
        List<Map<String, Object>> goodsListState = goodsMapper.selectGoodsGroupByCANCEL_REQ(paramMap.getMap());
        if (goodsListState.size() != 1) {
            throw new MsgException("반품 요청상태의 자산을 선택해 주세요");
        }
    }

    /** 자산 망실 처리 */
    @Transactional
    public void updateGoodsLost(ParamMap paramMap) {
        String[] gIdxs = paramMap.getString("gIdxs").split(",");

        Map<String, Object> lost = new HashMap<>();
        lost.put("lostDate", paramMap.getString("lostDate"));
        lost.put("lostBy", paramMap.getString("lostBy"));
        lost.put("lostReason", paramMap.getString("lostReason"));
        for (int i = 0; i < gIdxs.length; i++) {
            Goods goods = goodsRepo.findById(Long.parseLong(gIdxs[i])).get();
            Map<String, Object> gJson = goods.getGJson();
            gJson.put("lostInfo", lost);
            goods.setGJson(gJson);
            goods.setGState(GState.LOST);
            goodsRepo.save(goods);
        }
    }
}

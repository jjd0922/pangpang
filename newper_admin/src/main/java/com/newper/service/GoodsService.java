package com.newper.service;

import com.newper.component.SessionInfo;
import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.PoMapper;
import com.newper.mapper.ProcessMapper;
import com.newper.mapper.SpecMapper;
import com.newper.repository.*;
import com.newper.util.SpecFinder;
import lombok.RequiredArgsConstructor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GoodsService {

    private final SessionInfo sessionInfo;
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
    private final ProcessMapper processMapper;

    /** 자산 등록. 발주코드, 상품코드, 바코드만 먼저등록 입고검수가 되어야 입고확정스펙 나옴 */
    @Transactional
    public void insertGoods(int p_idx, int po_idx, String barcode) {
        Po po = poRepo.getReferenceById(po_idx);
        Product product = productRepo.findById(p_idx).get();


        InGroup inGroup = inGroupRepo.findByPo(po);

        if (inGroup.getIgState().equals(IgState.DONE)) {
            throw new MsgException("해당 발주건은 입고 완료되었습니다.");
        }

        Map<String, Object> gJson = new HashMap<>();
        updateGoodsBy(gJson);

        Goods goods= Goods.builder()
                .gBarcode(barcode)
                .po(po)
                .product(product)
                .gJson(gJson)
                .build();

        if (product.getPType1().equals(PType1.NORMAL)) {
            goods.setGState(GState.CHECK_DONE);
        }

        Goods save = goodsRepo.save(goods);

        poMapper.updategoods(po_idx, p_idx);
    }
    /**입고등록팝업 바코드 삭제 */
    @Transactional
    public void barcodeDelete(ParamMap paramMap){
        //조건 체크 GOODS.java 에 생성 함
        InGroup inGroup = inGroupRepo.findById(paramMap.getInt("igIdx")).get();
        if (inGroup.getIgState().equals(IgState.DONE)) {
            throw new MsgException("해당 발주건은 입고완료되었습니다.");
        }


        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).orElseThrow(()->new MsgException(("존재하지 않는 자산코드입니다.")));
        goodsRepo.delete(goods);
        goodsRepo.flush();
        poMapper.updategoods(goods.getPo().getPoIdx(), goods.getProduct().getPIdx());
    }

    /**입고검수 임시 그룹 생성 및 바코드 추가.*/
    @Transactional
    public String insertGoodsTemp(String idx, GgtType ggtType){
        //임시그룹 생성
        if (idx == null) {
            GoodsGroupTemp goodsGroupTemp = GoodsGroupTemp.builder()
                    .ggtType(ggtType)
                    .build();
            goodsGroupTempRepo.save(goodsGroupTemp);
            idx = goodsGroupTemp.getGgtIdx().toString();
        }
        return idx;
    }

    /**입고검수 임시 그룹 생성 및 바코드 추가.*/
    @Transactional
    public String insertGoodsTemp2(String idx, String[] gIdxs, GgtType ggtType) {
        //임시그룹 생성
        if (idx == null) {
            GoodsGroupTemp goodsGroupTemp = GoodsGroupTemp.builder()
                    .ggtType(ggtType)
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
                if (goods.getGState().equals(GState.CANCEL_NEED) || goods.getGState().equals(GState.CHECK_REQ)) {
                    goods.setGState(gState);
                    goodsRepo.save(goods);
                } else {
                    throw new MsgException("자산의 상태가 반품필요 & 입고검수인 자산만 반품요청할 수 있습니다.");
                }
                msg = "반품신청 완료";
            // 반품필요 자산을 입고검수 상태로 변경
            } else if (gState.equals(GState.CHECK_REQ)) {
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

    /** 같은 상품코드와 같은 옵션끼리만 */
    public List<Map<String, Object>> checkGoodsProduct(ParamMap paramMap) {
        paramMap.put("gIdxs", paramMap.getString("gIdxs").split(","));
        List<Map<String, Object>> goods = goodsMapper.goodsProductCheck(paramMap.getMap());
        if (goods.size() != 1) {
            throw new MsgException("발주매핑 작업을 하지 않은 같은 상품과 옵션인 자산만 선택해 주세요");
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

    /** 재검수 확정 */
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

            // 공정여부중 하나라도 Y가 있을경우 자산 상태값 Y 아니면 재검수
            List<ProcessNeed> processNeed = processNeedRepo.findByGoods_gIdxAndPnType(goods.getGIdx(), "YES");
            if (processNeed.size() != 0) {
                goods.setGState(GState.PROCESS);
            } else {
                goods.setGState(GState.CHECK_REQ);
            }

            goodsRepo.save(goods);
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

    /** 해당 자산들 입고검수 요청가능한지 자산값 체크 */
    public void goodsInCheck(ParamMap paramMap) {
        String[] gIdxs = paramMap.getString("gIdx").split(",");

        for (int i = 0; i < gIdxs.length; i++) {
            Goods goods = goodsRepo.findById(Long.parseLong(gIdxs[i])).get();
            if (!goods.getGState().equals(GState.RECEIVED)) {
                throw new MsgException(goods.getGBarcode() + "는 이미 입고검수한 자산입니다.");
            }
        }
    }

    /** 해당 자산들 해당 공정 가능한지 체크 */
    public void goodsProcessCheck(ParamMap paramMap) {
        String[] gIdx = paramMap.getString("gIdx").split(",");
        for (int i = 0; i < gIdx.length; i++) {
            Map<String, Object> processNeed = processMapper.goodsProcessCheck(gIdx[i], paramMap.getString("type"));
            System.out.println("pa: " + paramMap.entrySet());
            if (processNeed == null) {
                throw new MsgException(PnType.valueOf(paramMap.getString("type")).getOption() + "요청 할 수 없는 자산입니다.");
            }
        }
    }

    /** 해당자산들 재검수인지 자산값 체크 */
    public void goodsReCheck(ParamMap paramMap) {
        String[] gIdxs = paramMap.getString("gIdx").split(",");

        for (int i = 0; i < gIdxs.length; i++) {
            Goods goods = goodsRepo.findById(Long.parseLong(gIdxs[i])).get();
            if (!goods.getGState().equals(GState.RE_CHECK_NEED)) {
                throw new MsgException(goods.getGBarcode() + "는 재검수에 해당되는 자산이 아닙니다.");
            }
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

        List<Map<String, Object>> goodsListState = goodsMapper.selectGoodsGroupByState(paramMap.getMap());
        if (goodsListState.size() != 1) {
            throw new MsgException("반품 요청상태의 자산을 선택해 주세요");
        }
    }

    /** 자산상태값 변경 */
    @Transactional
    public void updateGoodsState(ParamMap paramMap) {
        GState gState = GState.valueOf(paramMap.getString("gState"));
        String[] gIdx = paramMap.getString("gIdx").split(",");
        // 재검수 완료 신청시 다른 공정이 있으면 상태값 공정 없으면 자산화 완료
        if (GState.RE_CHECK_DONE.equals(gState)) {
            for (int i = 0; i < gIdx.length; i++) {
                Goods goods = goodsRepo.findById(Long.parseLong(gIdx[i])).get();
                int check = processMapper.checkProcessNeedOtherByGoods(goods.getGIdx());
                if (check == 0) {
                    goods.setGState(GState.STOCK);
                    goods.setGStockState(GStockState.STOCK_REQ);
                } else {
                    goods.setGState(GState.PROCESS);
                }
                Map<String, Object> gJson = goods.getGJson();
                this.updateGoodsBy(gJson);
                goods.setGJson(gJson);

                goodsRepo.save(goods);
            }
            // 입고 검수 완료시
        } else if (GState.CHECK_DONE.equals(gState)){
            for (int i = 0; i < gIdx.length; i++) {
                Goods goods = goodsRepo.findById(Long.parseLong(gIdx[i])).get();

                // 해당 자산의 상태값이 입고검수중이 아니면
//                if (!goods.getGState().equals(GState.CHECK_ING)) {
//                    throw new MsgException("잘못된 접근입니다.");
//                }

                goods.setGState(gState);
                Map<String, Object> gJson = goods.getGJson();
                this.updateGoodsBy(gJson);
                goods.setGJson(gJson);

                goodsRepo.save(goods);
            }
        } else {
            // 그외
            for (int i = 0; i < gIdx.length; i++) {
                Goods goods = goodsRepo.findById(Long.parseLong(gIdx[i])).get();
                goods.setGState(gState);
                Map<String, Object> gJson = goods.getGJson();
                this.updateGoodsBy(gJson);
                goods.setGJson(gJson);
                goodsRepo.save(goods);
            }
        }
    }

    /** 자산의 상태값이 변할때 변한 사람과 그 시간을 저장 */
    @Transactional
    public void updateGoodsBy(Map<String, Object> gJson) {
        gJson.put("gStateId", sessionInfo.getId());
        gJson.put("gStateDate", LocalDate.now().toString());
    }
}

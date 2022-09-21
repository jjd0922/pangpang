package com.newper.service;

import com.newper.constant.CgState;
import com.newper.constant.IgState;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.ChecksMapper;
import com.newper.mapper.PoMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InService {

    private final InGroupRepo inGroupRepo;
    private final PoRepo poRepo;
    private final ProductRepo productRepo;
    private final PoMapper poMapper;

    private final GoodsRepo goodsRepo;
    private final ChecksMapper checkMapper;
    private final CheckGroupRepo checkGroupRepo;
    private final PoReceivedRepo poReceivedRepo;

    /** 발주서에 입고그룹 없는 경우 생성. */
    @Transactional
    public void insertInGroup(int po_idx){
        Po po = poRepo.getReferenceById(po_idx);
        InGroup ig = inGroupRepo.findByPo(po);

        if (ig == null) {
            ig = InGroup.builder()
                    .po(po)
                    .build();

            List<InProduct> inProductList = new ArrayList<>();
            for (int p_idx : poMapper.selectPoProductIdxList(po_idx)) {
                InProduct inProduct = InProduct.builder()
                        .inGroup(ig)
                        .product(productRepo.getReferenceById(p_idx))
                        .ipCount(0)
                        .build();
                inProductList.add(inProduct);
            }
            ig.setInProductList(inProductList);
            inGroupRepo.save(ig);
        }
    }

    public void updateInGroup(ParamMap paramMap) {
        InGroup inGroup = paramMap.mapParam(InGroup.class);
        inGroup.setIgState(IgState.DONE);
        inGroupRepo.save(inGroup);
    }

    /** 입고검수 작업요청 상태값 수정 */
    public void checkGroupStateUpdate(ParamMap paramMap) {
        int cgIdx = paramMap.getInt("cgIdx");
        Optional<CheckGroup> checkGroup = checkGroupRepo.findById(cgIdx);
        String state = paramMap.getString("state");

        if (state.equals("cancel")) {
            if (checkGroup.get().getCgState().equals(CgState.BEFORE)) {
                checkMapper.deleteCheckGoodsByCG_IDX(cgIdx);
                checkGroupRepo.deleteById(cgIdx);
            } else {
                throw new MsgException("해당건은 작업중이거나 작업이 완료되어 요청 취소가 불가합니다.");
            }
        } else if (state.equals("ing")) {
            if (checkGroup.get().getCgState().equals(CgState.BEFORE)) {
                checkGroup.get().setCgState(CgState.REQ);
                checkGroupRepo.save(checkGroup.get());
            } else {
                throw new MsgException("해당건은 작업중이거나 작업이 완료되어 처리가 불가능합니다.");
            }
        }

    }

    /** 입고등록시 완전 새로운 상품 입고시 입고상품 그룹 생성 */
    public void insertNewPoReceived(ParamMap paramMap) {
        Product product = paramMap.mapParam(Product.class);
        Po po = paramMap.mapParam(Po.class);

        PoReceived poReceived = PoReceived
                .builder()
                .product(product)
                .po(po)
                .porCount(0)
                .porCost(0)
                .porSellPrice(0)
                .porProfitTarget(0)
                .porOption(new ArrayList<>())
                .porMemo("기존 발주에 없는 상품")
                .build();

        poReceivedRepo.save(poReceived);
    }
}

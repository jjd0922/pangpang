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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InService {

    private final InGroupRepo inGroupRepo;
    private final InProductRepo inProductRepo;
    private final PoRepo poRepo;
    private final ProductRepo productRepo;
    private final PoMapper poMapper;

    private final GoodsRepo goodsRepo;
    private final ChecksMapper checkMapper;
    private final CheckGroupRepo checkGroupRepo;
    private final PoReceivedRepo poReceivedRepo;
    private final UserRepo userRepo;
    private final WarehouseRepo warehouseRepo;

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

    /** 입고완료 처리 */
    @Transactional
    public void updateInGroup(ParamMap paramMap) {
        System.out.println("sadf: " + paramMap.getMap().entrySet());
        Po po = poRepo.getReferenceById(paramMap.getInt("poIdx"));
        InGroup inGroup = inGroupRepo.findByPo(po);

        if (inGroup.getIgState().equals(IgState.DONE)) {
            throw new MsgException("이미 입고처리된 발주건 입니다.");
        }

        User user = userRepo.getReferenceById(paramMap.getInt("uIdx"));
        Warehouse warehouse = warehouseRepo.getReferenceById(paramMap.getInt("whIdx"));

        inGroup.setWarehouse(warehouse);
        inGroup.setUser(user);
        inGroup.setIgDate(LocalDate.now());
        inGroup.setIgMemo(paramMap.getString("igMemo"));
        inGroup.setIgDoneMemo("igDoneMemo");

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

    /** 입고등록시 새상품 등록 */
    @Transactional
    public void insertInProduct(ParamMap paramMap) {
        Po po = poRepo.getReferenceById(paramMap.getInt("poIdx"));
        Product product = productRepo.getReferenceById(paramMap.getInt("pIdx"));
        InGroup inGroup = inGroupRepo.findByPo(po);

        if (inGroup.getIgState().equals(IgState.DONE)) {
            throw new MsgException("해당 불주건은 이미 입고완료된 발주건 입니다.");
        }

        InProduct inProduct = inProductRepo.findByProductAndInGroup(product, inGroup);

        if (inProduct != null) {
            throw new MsgException("이미 등록되어있는 상품입니다.");
        }

        inProduct = InProduct
                .builder()
                .product(product)
                .inGroup(inGroup)
                .ipCount(0)
                .build();

        inProductRepo.save(inProduct);
    }
}

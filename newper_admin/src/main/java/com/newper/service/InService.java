package com.newper.service;

import com.newper.constant.CgState;
import com.newper.constant.GState;
import com.newper.constant.IgState;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.ChecksMapper;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.PoMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final GoodsMapper goodsMapper;
    private final CheckGoodsRepo checkGoodsRepo;

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
        inGroup.setIgTime(LocalTime.now());
        inGroup.setIgMemo(paramMap.getString("igMemo"));
        inGroup.setIgDoneMemo(paramMap.getString("igDoneMemo"));
        inGroup.setIgState(IgState.DONE);

        // 입고완료 처리시 매입상품 및 수량이 변경이 없는 경우에만 가능 그 외 강제입고완료
        if (paramMap.getString("compType").equals("normal")) {
            List<Integer> check = poMapper.checkPoProductInProduct(po.getPoIdx());
            for (int i = 0; i < check.size(); i++) {
                if (check.get(i) != 1) {
                    throw new MsgException("발주 품의 상품의 개수와 실입고된 상품의 개수가 다릅니다. 강제 입고 완료를 해주세요");
                }
            }
        }

        inGroupRepo.save(inGroup);
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

    /** 입고 검수 상태값 수정 */
    public void inCheckStateUpdate(ParamMap paramMap) {
        CheckGroup checkGroup = checkGroupRepo.findById(paramMap.getInt("cgIdx")).get();
        CgState cgState = CgState.valueOf(paramMap.getString("cgState"));

        // 진행중 요청시
        if (cgState.equals(CgState.REQ)) {
            if(!checkGroup.getCgState().equals(CgState.BEFORE)) {
                throw new MsgException("해당 검수 그룹은 진행할 수 없는 상태입니다.");
            }
        }

        // 완료처리시
        if (cgState.equals(CgState.FINISH)) {
            if(!checkGroup.getCgState().equals(CgState.REQ)) {
                throw new MsgException("해당 검수 그룹은 완료할 수 없는 상태입니다.");
            }
        }
    }
}

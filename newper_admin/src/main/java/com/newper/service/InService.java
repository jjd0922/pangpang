package com.newper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newper.constant.IgState;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.PoMapper;
import com.newper.repository.GoodsRepo;
import com.newper.repository.InGroupRepo;
import com.newper.repository.PoRepo;
import com.newper.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class InService {

    private final InGroupRepo inGroupRepo;
    private final PoRepo poRepo;
    private final ProductRepo productRepo;
    private final PoMapper poMapper;

    private final GoodsRepo goodsRepo;

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

    public void settingOptionAndSpec(List<Map<String, Object>> data) {
        for (int i = 0; i < data.size(); i++) {
            List<String> spec = poMapper.selectPoSpecBuy(Integer.parseInt(data.get(i).get("PP_IDX").toString()));
            String spec_str = "";
            for (int j = 0; j < spec.size(); j++) {
                spec_str += spec.get(j) + "/";
            }

            data.get(i).put("buy_spec", spec_str.substring(0, spec_str.length() - 1));

            List<String> spec_sell = poMapper.selectPoSpecSell(Integer.parseInt(data.get(i).get("PP_IDX").toString()));
            String spec_sell_str = "";
            for (int j = 0; j < spec_sell.size(); j++) {
                spec_sell_str += spec_sell.get(j) + "/";
            }
            data.get(i).put("sell_spec", spec_sell_str.substring(0, spec_sell_str.length() - 1));

            String option = data.get(i).get("PP_OPTION").toString();
            option = option.replace("[", "");
            option = option.replace("]", "");
            option = option.replace("{", "");
            option = option.replace("}", "");

            data.get(i).put("PP_OPTION", option);
        }
    }

    public void updateInGroup(ParamMap paramMap) {
        InGroup inGroup = paramMap.mapParam(InGroup.class);
        inGroup.setIgState(IgState.DONE);
        inGroupRepo.save(inGroup);
    }
}

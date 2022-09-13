package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.GState;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.ChecksMapper;
import com.newper.mapper.GoodsMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CheckService {

    private final CheckGroupRepo checkGroupRepo;
    private final ChecksMapper checkMapper;
    private final GoodsMapper goodsMapper;
    private final SpecListRepo specListRepo;
    private final SpecRepo specRepo;
    private final CheckGoodsRepo checkGoodsRepo;
    private final GoodsRepo goodsRepo;

    /** 검수 그룹 등록*/
    @Transactional
    public void insertCheckGroup(ParamMap paramMap){
        CheckGroup checkGroup = paramMap.mapParam(CheckGroup.class);

        Company company = paramMap.mapParam(Company.class);
        checkGroup.setCompany(company);

        User user = paramMap.mapParam(User.class);
        checkGroup.setUser(user);

        User user2 = User
                .builder()
                .uIdx(paramMap.getInt("uIdx2"))
                .build();
        checkGroup.setUser2(user2);

        checkGroupRepo.save(checkGroup);


        //check goods
        List<Long> gIdx = paramMap.getListLong("gIdx");

        for (int i = 0; i < gIdx.size(); i++) {
            Goods goods = Goods
                    .builder()
                    .gIdx(gIdx.get(i))
                    .build();

            CheckGoods checkGoods = CheckGoods
                    .builder()
                    .goods(goods)
                    .checkGroup(checkGroup)
                    .cgsExpectedCost(Integer.parseInt(goodsMapper.selectGoodsByG_IDX(gIdx.get(i)).get("TOTAL_PROCESS_COST").toString()))
                    .cgsRealCost(0)
                    .cgsType(paramMap.get("cgsType").toString())
                    .cgsCount(checkMapper.countCheckGroupByGoods(gIdx.get(i)) + 1)
                    .build();
        }

        long ggt_idx = paramMap.getLong("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }

    /** 입고검수 자산 입고 확정 스팩 */
    @Transactional
    public void updateCheckGoodsSpec(ParamMap paramMap, MultipartFile cgsFile) {
        // 자산 상태 체크
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
        if (!goods.getGState().equals(GState.RECEIVED)) {
            throw new MsgException("영업검수 요청되어 수정이 불가능합니다.");
        }

        CheckGoods checkGoods = checkGoodsRepo.findById(paramMap.getInt("cgsIdx")).get();

        List<String> cgsSpec1 = paramMap.getList("cgsSpec1");
        List<String> cgsSpec2 = paramMap.getList("cgsSpec2");
        List<String> specName = paramMap.getList("specName");

        String specConfirm = "";
        String specLookUp = "";
        List<Integer> specConfirmList = new ArrayList<>();



        // 입고(확정) SPEC 세팅
        if (cgsSpec1 != null) {
            for (int i = 0; i < cgsSpec1.size(); i++) {
                SpecList specList = specListRepo.findSpecListBySpeclNameAndSpeclValue(specName.get(i), cgsSpec1.get(i));

                if (specList == null) {
                    specList = SpecList
                            .builder()
                            .speclName(specName.get(i))
                            .speclValue(cgsSpec1.get(i))
                            .build();
                    specListRepo.save(specList);
                }
                specConfirmList.add(specList.getSpeclIdx());
            }
            Collections.sort(specConfirmList);

            for (int i = 0; i < cgsSpec1.size(); i++) {
                specConfirm += specConfirmList.get(i) + ", ";
                specLookUp += specName.get(i) + ":" + cgsSpec1.get(i) + "/";
            }
            specConfirm = specConfirm.substring(0, specConfirm.length() - 2);
            specLookUp = specLookUp.substring(0, specLookUp.length() - 1);

            Spec spec = specRepo.findSpecBySpecConfirm(specConfirm);
            if (spec == null) {
                spec = Spec
                        .builder()
                        .specConfirm(specConfirm)
                        .specLookup(specLookUp)
                        .build();
                specRepo.save(spec);
            }

            checkGoods.setSpec(spec);

            specConfirm = "";
            specLookUp = "";
            specConfirmList.clear();
        }

        // 가공(예정) SPEC 세팅
        if (cgsSpec2 != null) {
            for (int i = 0; i < cgsSpec2.size(); i++) {
                SpecList specList = specListRepo.findSpecListBySpeclNameAndSpeclValue(specName.get(i), cgsSpec2.get(i));

                if (specList == null) {
                    specList = SpecList
                            .builder()
                            .speclName(specName.get(i))
                            .speclValue(cgsSpec2.get(i))
                            .build();
                    specListRepo.save(specList);
                }
                specConfirmList.add(specList.getSpeclIdx());
            }
            Collections.sort(specConfirmList);

            for (int i = 0; i < cgsSpec2.size(); i++) {
                specConfirm += specConfirmList.get(i) + ", ";
                specLookUp += specName.get(i) + ":" + cgsSpec2.get(i) + "/";
            }

            specConfirm = specConfirm.substring(0, specConfirm.length() - 2);
            specLookUp = specLookUp.substring(0, specLookUp.length() - 1);

            Spec spec = specRepo.findSpecBySpecConfirm(specConfirm);
            if (spec == null) {
                spec = Spec
                        .builder()
                        .specConfirm(specConfirm)
                        .specLookup(specLookUp)
                        .build();
                specRepo.save(spec);
            }

            checkGoods.setSpec2(spec);
        }

        // 첨부 파일
        if (cgsFile == null || cgsFile.isEmpty()) {
            checkGoods.setCgsFile("");
            checkGoods.setCgsFileName("");
        } else {
            String cgsFilePath = Common.uploadFilePath(cgsFile, "in/check_goods/", AdminBucket.SECRET);
            checkGoods.setCgsFile(cgsFilePath);
            checkGoods.setCgsFileName(cgsFile.getOriginalFilename());
        }

        checkGoodsRepo.save(checkGoods);

        // 자산 상태 변경
        goods.setGState(GState.CHECK_NEED);
        goodsRepo.save(goods);
    }
}

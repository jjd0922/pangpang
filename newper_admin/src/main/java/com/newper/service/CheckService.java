package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.GState;
import com.newper.constant.PnType;
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

import java.util.*;

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
    private final PoReceivedRepo poReceivedRepo;
    private final ProcessNeedRepo processNeedRepo;

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
    public void updateCheckGoodsSpec(ParamMap paramMap, MultipartFile[] paintFile, MultipartFile[] fixFile, MultipartFile[] processFile) {
        // 자산 상태 체크
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
        if (!goods.getGState().equals(GState.RECEIVED)) {
            throw new MsgException("영업검수 요청되어 수정이 불가능합니다.");
        }

        PoReceived poReceived = PoReceived.builder().build();

        List<String> cgsSpec1 = paramMap.getList("porSpec1");
        List<String> cgsSpec2 = paramMap.getList("porSpec2");
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

            poReceived.setSpec(spec);

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

            poReceived.setSpec2(spec);
        }

        // 상품setting
        Product product = Product
                .builder()
                .pIdx(paramMap.getInt("pIdx"))
                .build();
        poReceived.setProduct(product);
        poReceivedRepo.save(poReceived);

        // PROCESS_NEED
        //도색
        if (!paramMap.get("paintCost").equals("")) {
            ProcessNeed processNeed = ProcessNeed
                    .builder()
                    .goods(goods)
                    .pnContent(paramMap.get("paintMemo").toString())
                    .pnExpectedCost(paramMap.getInt("paintCost"))
                    .pnType(PnType.PAINT)
                    .pnCount(0)
                    .build();

            if (paintFile.length != 0) {
                Map<String, Object> pnLookUp = new HashMap<>();
                List<String> paintFileStr = new ArrayList<>();
                List<String> paintFileName = new ArrayList<>();

                for (int i = 0; i < paintFile.length; i++) {
                    if (paintFile[i] == null || paintFile[i].isEmpty()) {
                        continue;
                    } else {
                        paintFileStr.add(Common.uploadFilePath(paintFile[i], "goods/" + goods.getGIdx() + "/", AdminBucket.SECRET));
                        paintFileName.add(paintFile[i].getOriginalFilename());
                    }
                }

                pnLookUp.put("paintFile", paintFileStr);
                pnLookUp.put("paintFileName", paintFileName);

                processNeed.setPnLookup(pnLookUp);
            }

            processNeedRepo.save(processNeed);
        }

        //수리
        if (!paramMap.get("fixCost").equals("")) {
            ProcessNeed processNeed = ProcessNeed
                    .builder()
                    .goods(goods)
                    .pnContent(paramMap.get("fixMemo").toString())
                    .pnExpectedCost(paramMap.getInt("fixCost"))
                    .pnType(PnType.PAINT)
                    .pnCount(0)
                    .build();

            if (fixFile.length != 0) {
                Map<String, Object> pnLookUp = new HashMap<>();
                List<String> fixFileStr = new ArrayList<>();
                List<String> fixFileName = new ArrayList<>();

                for (int i = 0; i < fixFile.length; i++) {
                    if (fixFile[i] == null || fixFile[i].isEmpty()) {
                        continue;
                    } else {
                        fixFileStr.add(Common.uploadFilePath(fixFile[i], "goods/" + goods.getGIdx() + "/", AdminBucket.SECRET));
                        fixFileName.add(fixFile[i].getOriginalFilename());
                    }
                }

                pnLookUp.put("fixFile", fixFileStr);
                pnLookUp.put("fixFileName", fixFileName);

                processNeed.setPnLookup(pnLookUp);
            }

            processNeedRepo.save(processNeed);
        }

        //가공
        if (!paramMap.get("processCost").equals("")) {
            ProcessNeed processNeed = ProcessNeed
                    .builder()
                    .goods(goods)
                    .pnContent(paramMap.get("processMemo").toString())
                    .pnExpectedCost(paramMap.getInt("processCost"))
                    .pnType(PnType.PAINT)
                    .pnCount(0)
                    .build();

            if (processFile.length != 0) {
                Map<String, Object> pnLookUp = new HashMap<>();
                List<String> processFileStr = new ArrayList<>();
                List<String> processFileName = new ArrayList<>();

                for (int i = 0; i < processFile.length; i++) {
                    if (processFile[i] == null || processFile[i].isEmpty()) {
                        continue;
                    } else {
                        processFileStr.add(Common.uploadFilePath(processFile[i], "goods/" + goods.getGIdx() + "/", AdminBucket.SECRET));
                        processFileName.add(processFile[i].getOriginalFilename());
                    }
                }

                pnLookUp.put("processFile", processFileStr);
                pnLookUp.put("processFileName", processFileName);

                processNeed.setPnLookup(pnLookUp);
            }

            processNeedRepo.save(processNeed);
        }



        // 자산 상태 변경
        goods.setGState(GState.CHECK_NEED);
        goodsRepo.save(goods);
    }
}

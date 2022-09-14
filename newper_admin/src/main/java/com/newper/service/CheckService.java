package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.GState;
import com.newper.constant.PnType;
import com.newper.constant.PsType;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.ChecksMapper;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.PoMapper;
import com.newper.mapper.SpecMapper;
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
    private final PoMapper poMapper;
    private final SpecMapper specMapper;

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
                    .gState(GState.CHECK_NEED)
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

            checkGoodsRepo.save(checkGoods);
        }

        long ggt_idx = paramMap.getLong("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }

    /** 입고검수 자산 입고 확정 스팩 */
    @Transactional
    public void updateCheckGoodsSpec(ParamMap paramMap, MultipartFile[] gFile) {
        // 자산 상태 체크
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
        if (!goods.getGState().equals(GState.RECEIVED)) {
            throw new MsgException("영업검수 요청되어 수정이 불가능합니다.");
        }

        //실입고 상품
        List<String> porSpec1 = paramMap.getList("porSpec1");
        List<String> porSpec2 = paramMap.getList("porSpec2");
        List<String> specName = paramMap.getList("specName");

        String specConfirm = "";
        String specLookUp = "";
        List<Integer> specConfirmList = new ArrayList<>();

        int pIdx = paramMap.getInt("pIdx");
        int specIdx = 0;
        int specIdx2 = 0;
        List<Map<String, Object>> option = new ArrayList<>();
        //옵션 세팅
        List<String> optionList = paramMap.getList("productOption");
        for (int i = 0; i < optionList.size(); i++) {
            if (!optionList.get(i).equals("")) {
                Common.putOption(option, optionList.get(i));
            }
        }






        // 입고(확정) SPEC 세팅
        if (porSpec1 != null) {
            for (int i = 0; i < porSpec1.size(); i++) {
                SpecList specList = specListRepo.findSpecListBySpeclNameAndSpeclValue(specName.get(i), porSpec1.get(i));

                if (specList == null) {
                    specList = SpecList
                            .builder()
                            .speclName(specName.get(i))
                            .speclValue(porSpec1.get(i))
                            .build();
                    specListRepo.save(specList);
                }
                specConfirmList.add(specList.getSpeclIdx());

                specLookUp += specName.get(i) + ":" + porSpec1.get(i) + "/";
            }
            specLookUp = specLookUp.substring(0, specLookUp.length() - 1);
            Collections.sort(specConfirmList);

            for (int i = 0; i < porSpec1.size(); i++) {
                specConfirm += specConfirmList.get(i) + ", ";
            }
            specConfirm = specConfirm.substring(0, specConfirm.length() - 2);

            Spec spec = specRepo.findSpecBySpecConfirm(specConfirm);
            if (spec == null) {
                spec = Spec
                        .builder()
                        .specConfirm(specConfirm)
                        .specLookup(specLookUp)
                        .build();
                specRepo.save(spec);
            } else {
                specIdx = spec.getSpecIdx();
            }

            specConfirm = "";
            specLookUp = "";
            specConfirmList.clear();
        }

        // 가공(예정) SPEC 세팅
        if (porSpec2 != null) {
            for (int i = 0; i < porSpec2.size(); i++) {
                SpecList specList = specListRepo.findSpecListBySpeclNameAndSpeclValue(specName.get(i), porSpec2.get(i));

                if (specList == null) {
                    specList = SpecList
                            .builder()
                            .speclName(specName.get(i))
                            .speclValue(porSpec2.get(i))
                            .build();
                    specListRepo.save(specList);
                }
                specConfirmList.add(specList.getSpeclIdx());
            }
            Collections.sort(specConfirmList);

            for (int i = 0; i < porSpec2.size(); i++) {
                specConfirm += specConfirmList.get(i) + ", ";
                specLookUp += specName.get(i) + ":" + porSpec2.get(i) + "/";
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
            specIdx2 = spec.getSpecIdx();
        }

        // 이미 있는 실입고 상품인지 조회
        PoReceived poReceived = poMapper.selectPoReceivedByProductAndPoAndSpecAndOption(pIdx, paramMap.getInt("poIdx"), specIdx, option.toString());
        if (poReceived == null) {
            Po po = Po
                    .builder()
                    .poIdx(paramMap.getInt("poIdx"))
                    .build();

            Product product = Product
                    .builder()
                    .pIdx(paramMap.getInt("pIdx"))
                    .build();

            Spec spec = Spec
                    .builder()
                    .specIdx(specIdx)
                    .build();

            poReceived = PoReceived
                    .builder()
                    .po(po)
                    .product(product)
                    .porOption(option)
                    .spec(spec)
                    .porCost(0)
                    .porCount(0)
                    .porMemo("")
                    .porSellPrice(0)
                    .porProfitTarget(0)
                    .build();
        } else {
            poReceived = PoReceived
                    .builder()
                    .porCount(poReceived.getPorCount() + 1)
                    .build();
        }

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

            processNeedRepo.save(processNeed);

            ProcessSpec processSpec = ProcessSpec
                    .builder()
                    .processNeed(processNeed)
                    .psType(PsType.EXPECTED)
                    .psExpectedCost(paramMap.getInt("processCost"))
                    .psRealCost(0)
                    .build();
        }

        // 실입고상품 자산 인덱스 매핑
        goods.setPoReceived(poReceived);

        // 자산 상태 변경
        goods.setGState(GState.CHECK);

        // 자산 이미지 업로드
        if (gFile != null) {
            Map<String, Object> gJson = new HashMap<>();
            List<String> file = new ArrayList<>();
            List<String> fileName = new ArrayList<>();

            for (int i = 0; i < gFile.length; i++) {
                if (gFile[i] != null || !gFile[i].isEmpty()) {
                    file.add(Common.uploadFilePath(gFile[i], "goods/photo/" + goods.getGIdx(), AdminBucket.SECRET));
                    fileName.add(gFile[i].getOriginalFilename());
                }
            }

            gJson.put("gFile", file);
            gJson.put("gFileName", fileName);
            goods.setGJson(gJson);
        }

        goodsRepo.save(goods);
    }
}

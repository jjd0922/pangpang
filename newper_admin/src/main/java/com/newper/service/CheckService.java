package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.ChecksMapper;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.PoMapper;
import com.newper.mapper.SpecMapper;
import com.newper.repository.*;
import com.newper.util.SpecFinder;
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
    private final SpecMapper specMapper;
    private final ProcessGroupRepo processGroupRepo;
    private final ProcessNeedRepo processNeedRepo;

    /** 검수 그룹 등록*/
    @Transactional
    public void insertCheckGroup(ParamMap paramMap){
        CheckGroup checkGroup = paramMap.mapParam(CheckGroup.class);

        Company company = paramMap.mapParam(Company.class);
        checkGroup.setCompany(company);

        User user = paramMap.mapParam(User.class);
        checkGroup.setUser(user);

        checkGroupRepo.save(checkGroup);

        GState gState = GState.valueOf(paramMap.getString("gState"));
        CgsType cgsType = CgsType.valueOf(paramMap.getString("cgsType"));


        //check goods
        List<Long> gIdx = paramMap.getListLong("gIdx");

        for (int i = 0; i < gIdx.size(); i++) {
            Goods goods = goodsRepo.findById(gIdx.get(i)).get();
            goods.setGState(gState);
            goodsRepo.save(goods);

            CheckGoods checkGoods = CheckGoods
                    .builder()
                    .goods(goods)
                    .checkGroup(checkGroup)
                    .cgsExpectedCost(0)
                    .cgsRealCost(0)
                    .cgsType(cgsType.toString())
                    .cgsCount(0)
                    .build();

            checkGoodsRepo.save(checkGoods);
        }

        long ggt_idx = paramMap.getLong("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }

    /** 입고검수 자산 입고검수 정보 자산에 UPDATE */
    @Transactional
    public void goodsInCheck(ParamMap paramMap, MultipartFile[] gFile) {
        // 자산 상태 체크
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
        if (!goods.getGState().equals(GState.RECEIVED)) {
            throw new MsgException("영업검수 요청되어 수정이 불가능합니다.");
        }

        //IMEI 저장
        goods.setGImei(paramMap.getString("gImei"));

        //옵션 세팅
        List<Map<String, Object>> option = new ArrayList<>();
        List<String> optionList = paramMap.getList("productOption");
        Common.putOption(option, optionList);

        goods.setGOption(option);

        //메모 저장
        goods.setGMemo(paramMap.getString("gMemo"));

        Map<String, Object> gJson = new HashMap<>();
        // 자산 이미지 업로드
        if (gFile != null) {
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
        }

        // 예상 공정 항목
        // 도색
        gJson.put("paintMemo", paramMap.getString("paintMemo"));
        gJson.put("paintCost", paramMap.getString("paintCost"));
        // 수리
        gJson.put("fixMemo", paramMap.getString("fixMemo"));
        gJson.put("fixCost", paramMap.getString("fixCost"));
        // 가공
        gJson.put("processMemo", paramMap.getString("processMemo"));
        gJson.put("processCost", paramMap.getString("processCost"));

        goods.setGJson(gJson);

        // 입고SPEC(확정)
        SpecFinder specFinder = new SpecFinder(specMapper, specListRepo, specRepo);
        List<String> specName = paramMap.getList("specName");
        List<String> specValue1 = paramMap.getList("spec1");
        gJson.put("inSpec", specFinder.findSpec(specName, specValue1));

        //가공SPEC(예정)
        List<String> specValue2 = paramMap.getList("spec2");
        gJson.put("processSpec", specFinder.findSpec(specName, specValue2));

        // 자산 상태 변경
        goods.setGState(GState.CHECK);

        goodsRepo.save(goods);
    }

    /** 공정필요 데이터 생성 (가공, 수리, 도색) */
    @Transactional
    public void insertProcessGroup(ParamMap paramMap) {
        ProcessGroup processGroup = paramMap.mapParam(ProcessGroup.class);

        Company company = paramMap.mapParam(Company.class);
        processGroup.setCompany(company);

        User user = paramMap.mapParam(User.class);
        processGroup.setUser(user);

        processGroup.setPgType(paramMap.getString("pgType"));
        processGroup.setPgDoneMemo("");
        processGroup.setPgLastState("");
        processGroupRepo.save(processGroup);

        //check goods
        List<Long> gIdx = paramMap.getListLong("gIdx");

        for (int i = 0; i < gIdx.size(); i++) {
            Goods goods = Goods
                    .builder()
                    .gIdx(gIdx.get(i))
                    .gState(GState.PROCESS)
                    .build();


            ProcessNeed processNeed = processNeedRepo.findTopByGoods_gIdxAndPnProcessAndPnTypeAndProcessGroup_pgIdxIsNullOrderByPnIdxDesc(goods.getGIdx(), "Y", paramMap.getString("pgType"));
            if (processNeed == null) {
                continue;
            }
            processNeed.setProcessGroup(processGroup);

            processNeedRepo.save(processNeed);
        }

        long ggt_idx = paramMap.getLong("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }
}

package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.*;
import com.newper.repository.*;
import com.newper.util.SpecFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
    private final ProcessSpecRepo processSpecRepo;
    private final ProcessMapper processMapper;
    private final ProcessService processService;

    /** 검수 그룹 등록*/
    @Transactional
    public void insertCheckGroup(ParamMap paramMap){
        CheckGroup checkGroup = paramMap.mapParam(CheckGroup.class);

        Company company = paramMap.mapParam(Company.class);
        checkGroup.setCompany(company);

        User user = paramMap.mapParam(User.class);
        checkGroup.setUser(user);

        checkGroup.setCgType(CgType.valueOf(paramMap.getString("cgType")));

        checkGroupRepo.save(checkGroup);

        GState gState = GState.valueOf(paramMap.getString("gState"));
        CgsType cgsType = CgsType.valueOf(paramMap.getString("cgsType"));


        //check goods
        List<Long> gIdx = processMapper.selectGoodsTemp(paramMap.getLong("ggt_idx"));

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

    /** 입고검수 자산 정보 UPDATE */
    @Transactional
    public void goodsInCheck(ParamMap paramMap, MultipartFile[] gFile) {
        // 자산 상태 체크
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();

        //IMEI 저장
        goods.setGImei(paramMap.getString("gImei"));

        //옵션 세팅
        List<Map<String, Object>> option = new ArrayList<>();
        List<String> optionList = paramMap.getList("productOption");
        Common.putOption(option, optionList);

        goods.setGOption(option);

        //메모 저장
        goods.setGMemo(paramMap.getString("gMemo"));

        Map<String, Object> gJson = goods.getGJson();
        // 자산 이미지 업로드
        if (!gFile[0].getOriginalFilename().equals("")) {
            List<String> file = (List<String>) gJson.get("gFile");
            List<String> fileName = (List<String>) gJson.get("gFileName");

            for (int i = 0; i < gFile.length; i++) {
                file.add(Common.uploadFilePath(gFile[i], "goods/photo/" + goods.getGIdx() + "/", AdminBucket.SECRET));
                fileName.add(gFile[i].getOriginalFilename());
            }

            gJson.put("gFile", file);
            gJson.put("gFileName", fileName);
        }

        // 입고SPEC(확정)
        List<String> inSpec2 = paramMap.getList("inSpec2");
        gJson.put("inSpec2", inSpec2);

        // CHECK_GOODS 예상비용 업데이트
        int expectedCost = paramMap.getIntZero("paintCost") + paramMap.getIntZero("fixCost") + paramMap.getIntZero("processCost");
        CheckGoods checkGoods = checkGoodsRepo.findById(paramMap.getIntZero("cgsIdx")).get();
        checkGoods.setCgsExpectedCost(expectedCost);
        checkGoodsRepo.save(checkGoods);

        // PROCESS_NEED 생성
        processService.insertProcessNeed(goods, paramMap, PnType.PAINT);
        processService.insertProcessNeed(goods, paramMap, PnType.FIX);
        processService.insertProcessNeed(goods, paramMap, PnType.PROCESS);

        List<String> pSepc1 = paramMap.getList("pSepc1");
        gJson.put("pSpec1", pSepc1);
        goods.setGJson(gJson);
        goodsRepo.save(goods);
    }

    /** 영업검수 자산 정보 확정 */
    @Transactional
    public void goodsInfoComplete(ParamMap paramMap, MultipartFile[] gFile) {
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();

        Map<String, Object> gJson = goods.getGJson();
        // 자산 이미지 업로드
        if (!gFile[0].getOriginalFilename().equals("")) {
            List<String> file = (List<String>) gJson.get("gFile");
            List<String> fileName = (List<String>) gJson.get("gFileName");

            for (int i = 0; i < gFile.length; i++) {
                file.add(Common.uploadFilePath(gFile[i], "goods/photo/" + goods.getGIdx() + "/", AdminBucket.SECRET));
                fileName.add(gFile[i].getOriginalFilename());
            }

            gJson.put("gFile", file);
            gJson.put("gFileName", fileName);
        }

        goods.setGVendor(paramMap.getString("gVendor"));
        goods.setGRank(GRank.valueOf(paramMap.getString("gRank")));
        goods.setGMemo(paramMap.getString("gMemo"));


        List<String> gOption = paramMap.getList("goodsOption");
        List<Map<String, Object>> optionList = new ArrayList<>();
        for (int i = 0; i < gOption.size(); i++) {
            if (!gOption.get(i).equals("")) {
                Map<String, Object> optionMap = new HashMap<>();
                optionMap.put("title", gOption.get(i).split(":")[0]);
                optionMap.put("value", gOption.get(i).split(":")[1]);
                optionList.add(optionMap);
            }
        }
        goods.setGOption(optionList);

        //판매 확정 SPEC
        SpecFinder specFinder = new SpecFinder(specMapper, specListRepo, specRepo);
        List<String> specName = paramMap.getList("specName");
        List<String> sellSpec2Value = paramMap.getList("sellSpec2");
        Spec sellSpec2 = specFinder.findSpec(specName, sellSpec2Value);
        goods.setSellSpec(sellSpec2);
        gJson.put("sellSpec2", sellSpec2Value);

        processService.insertProcessNeed(goods, paramMap, PnType.PAINT);
        processService.insertProcessNeed(goods, paramMap, PnType.FIX);
        processService.insertProcessNeed(goods, paramMap, PnType.PROCESS);

        goods.setGJson(gJson);
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
        processGroup.setPgCheckMemo("");
        processGroup.setPgEndDate(null);
        processGroupRepo.save(processGroup);

        Map<String, Object> param = new HashMap<>();
        param.put("pnType", paramMap.getString("type"));
        param.put("pnState", "NEED");
        //check goods
        List<Long> gIdx = processMapper.selectGoodsTemp(paramMap.getLong("ggt_idx"));
        for (int i = 0; i < gIdx.size(); i++) {
            param.put("gIdx", gIdx.get(i));

            ProcessNeed processNeed = processMapper.selectProcessNeedEntity(param);
            if (processNeed == null) {
                throw new MsgException("잘못된 접근입니다.");
            }
            processNeed.setProcessGroup(processGroup);
            processNeed.setPnState(PnState.REQUEST);

            processMapper.updateProcessNeed(processNeed);
//            processNeedRepo.save(processNeed);
        }

        long ggt_idx = paramMap.getLong("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }
}

package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.SessionInfo;
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

    private final SessionInfo sessionInfo;

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

    private final GoodsService goodsService;

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
        CgType cgType = CgType.valueOf(paramMap.getString("cgType"));
        CgsType cgsType = CgsType.valueOf(paramMap.getString("cgsType"));

        //check goods
        List<Long> gIdx = processMapper.selectGoodsTemp(paramMap.getLong("ggt_idx"));

        for (int i = 0; i < gIdx.size(); i++) {
            Goods goods = goodsRepo.findById(gIdx.get(i)).get();
            goods.setGState(gState);

            Map<String,Object> gJson = goods.getGJson();
            goodsService.updateGoodsBy(gJson);

            if (cgType.equals(CgType.RE)) {
                gJson.put("reCgIdx", checkGroup.getCgIdx());
            } else if (cgType.equals(CgType.IN)) {
                gJson.put("inCgIdx", checkGroup.getCgIdx());
            } else {

            }

            goodsRepo.save(goods);

            int count = checkMapper.selectCheckGoodsCount(goods.getGIdx(), CgsType.IN.name());

            CheckGoods checkGoods = CheckGoods
                    .builder()
                    .goods(goods)
                    .checkGroup(checkGroup)
                    .cgsExpectedCost(0)
                    .cgsRealCost(0)
                    .cgsType(cgsType.toString())
                    .cgsCount(count + 1)
                    .cgsJson(new HashMap<>())
                    .build();

            checkGoodsRepo.save(checkGoods);
        }

        long ggt_idx = paramMap.getLong("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }

    /** 입고검수 자산 정보 UPDATE */
    @Transactional
    public void saveInCheckReport(ParamMap paramMap, MultipartFile[] gFile) {
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

//        // PROCESS_NEED 생성
//        processService.insertProcessNeed(goods, paramMap, PnType.PAINT);
//        processService.insertProcessNeed(goods, paramMap, PnType.FIX);
//        processService.insertProcessNeed(goods, paramMap, PnType.PROCESS);

        goods.setGJson(gJson);
        goodsRepo.save(goods);
    }

    /** 영업검수 자산 정보 확정 */
    @Transactional
    public void saveGoodsReport(ParamMap paramMap, MultipartFile[] gFile) {
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
                optionMap.put("values", gOption.get(i).split(":")[1]);
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

        // 도색
        int paintIdx = paramMap.getIntZero("paintIdx");
        String paintContent = paramMap.getString("paintContent");
        int paintCost = paramMap.getIntZero("paintCost");

        if (paintIdx == 0 && !paintContent.replaceAll(" ","").equals("") && paintCost != 0) {
            int pnIdx = processService.insertProcessNeed(paramMap, goods, PnType.PAINT);
            paramMap.put("paintIdx", pnIdx);
        } else if (paintIdx != 0){
            processService.updateProcessNeed(paramMap, paintIdx);
        }


        // 수리
        int fixIdx = paramMap.getIntZero("fixIdx");
        String fixContent = paramMap.getString("fixContent");
        int fixCost = paramMap.getIntZero("fixCost");

        if (fixIdx == 0 && !fixContent.replaceAll(" ","").equals("") && fixCost != 0) {
            int pnIdx = processService.insertProcessNeed(paramMap, goods, PnType.FIX);
            paramMap.put("fixIdx", pnIdx);
        } else if (fixIdx != 0) {
            processService.updateProcessNeed(paramMap, fixIdx);
        }

        // 가공
        int processIdx = paramMap.getIntZero("processIdx");
        String processContent = paramMap.getString("processContent");
        int processCost = paramMap.getIntZero("processCost");

        if (processIdx == 0 && !processContent.replaceAll(" ","").equals("") && processCost != 0) {
            int pnIdx = processService.insertProcessNeed(paramMap, goods, PnType.PROCESS);
            paramMap.put("processIdx", pnIdx);
        } else if (processIdx != 0) {
            processService.updateProcessNeed(paramMap, processIdx);
        }

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

        String type = paramMap.getString("type").toLowerCase();
        PnType pnType = PnType.valueOf(paramMap.getString("type"));
        //check goods
        List<Long> gIdx = processMapper.selectGoodsTemp(paramMap.getLong("ggt_idx"));
        for (int i = 0; i < gIdx.size(); i++) {
            ProcessNeed processNeed = processNeedRepo.findByGoodsAndPnTypeAndPnProcessAndPnState(goodsRepo.getReferenceById(gIdx.get(i)), pnType, PnProcess.Y, PnState.NEED);

            if (processNeed == null) {
                throw new MsgException("잘못된 접근입니다.");
            }

            processNeed.setProcessGroup(processGroup);
            processNeed.setPnState(PnState.REQUEST);

            Goods goods = goodsRepo.findById(gIdx.get(i)).get();
            Map<String, Object> gJson = goods.getGJson();
            goodsService.updateGoodsBy(gJson);
            gJson.put("pnContent", processNeed.getPnContent());
            gJson.put("pnState", processNeed.getPnState().toString());
            gJson.put("pnId", sessionInfo.getId());
            gJson.put("pnDate", LocalDate.now().toString());


            processNeedRepo.save(processNeed);
        }

        long ggt_idx = paramMap.getLong("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }

    /** 영업검수 확정 **/
    @Transactional
    public void saveCheckReport(ParamMap paramMap, MultipartFile[] gFile) {
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();

        String paint = paramMap.getString("paintProcess");
        String fix = paramMap.getString("fixProcess");
        String process = paramMap.getString("processProcess");

        // 모든 공정이 민진행일경우 상품화 완료
        if (paint.equals("N") && fix.equals("N") && process.equals("N")) {
            goods.setGState(GState.STOCK);
            goods.setGStockState(GStockState.STOCK_REQ);
        } else {
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
                    optionMap.put("values", gOption.get(i).split(":")[1]);
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

            // 도색
            int paintIdx = paramMap.getIntZero("paintIdx");
            String paintContent = paramMap.getString("paintContent");
            int paintCost = paramMap.getIntZero("paintCost");

            if (paintIdx == 0 && !paintContent.replaceAll(" ","").equals("") && paintCost != 0) {
                int pnIdx = processService.insertProcessNeed(paramMap, goods, PnType.PAINT);
                paramMap.put("paintIdx", pnIdx);
            } else if (paintIdx != 0){
                processService.updateProcessNeed(paramMap, paintIdx);
            }


            // 수리
            int fixIdx = paramMap.getIntZero("fixIdx");
            String fixContent = paramMap.getString("fixContent");
            int fixCost = paramMap.getIntZero("fixCost");

            if (fixIdx == 0 && !fixContent.replaceAll(" ","").equals("") && fixCost != 0) {
                int pnIdx = processService.insertProcessNeed(paramMap, goods, PnType.FIX);
                paramMap.put("fixIdx", pnIdx);
            } else if (fixIdx != 0) {
                processService.updateProcessNeed(paramMap, fixIdx);
            }

            // 가공
            int processIdx = paramMap.getIntZero("processIdx");
            String processContent = paramMap.getString("processContent");
            int processCost = paramMap.getIntZero("processCost");

            if (processIdx == 0 && !processContent.replaceAll(" ","").equals("") && processCost != 0) {
                int pnIdx = processService.insertProcessNeed(paramMap, goods, PnType.PROCESS);
                paramMap.put("processIdx", pnIdx);
            } else if (processIdx != 0) {
                processService.updateProcessNeed(paramMap, processIdx);
            }


            String paintProcess = paramMap.getString("paintProcess");
            String fixProcess = paramMap.getString("fixProcess");
            String processProcess = paramMap.getString("processProcess");

            if (paintProcess.equals("N") && fixProcess.equals("N") && processProcess.equals("N")) {
                goods.setGState(GState.STOCK);
                goods.setGStockState(GStockState.STOCK_REQ);

                gJson.put("paintProcess", "N");
                gJson.put("fixProcess", "N");
                gJson.put("processProcess", "N");
            } else {
                goods.setGState(GState.PROCESS);

                gJson.put("paintProcess", paintProcess);
                gJson.put("fixProcess", fixProcess);
                gJson.put("processProcess", processProcess);
            }

            goods.setGJson(gJson);
        }
        goodsRepo.save(goods);

    }
















    /** 자산 검수 완료처리 */
    @Transactional
    public void checkDoneGoods(ParamMap paramMap) {
        String[] gIdx = paramMap.getString("gIdx").split(",");
        int cgIdx = paramMap.getInt("cgIdx");

        for (int i = 0; i < gIdx.length; i++) {
            Goods goods =  goodsRepo.findById(Long.parseLong(gIdx[i])).get();


        }
    }

    /** 그룹 검수 완료처리 */
    @Transactional
    public void checkDoneGroup(ParamMap paramMap) {
        int cgIdx = paramMap.getInt("cgIdx");

    }
}

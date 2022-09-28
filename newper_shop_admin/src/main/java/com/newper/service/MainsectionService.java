package com.newper.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.MsType;
import com.newper.constant.etc.MsJson;
import com.newper.dto.ParamMap;
import com.newper.entity.MainSection;
import com.newper.entity.MainSectionBanner;
import com.newper.entity.ShopProduct;
import com.newper.exception.MsgException;
import com.newper.mapper.MainSectionMapper;
import com.newper.repository.MainSectionBannerRepo;
import com.newper.repository.MainSectionRepo;
import com.newper.repository.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainsectionService {
    private final MainSectionRepo mainSectionRepo;
    private final MainSectionBannerRepo mainSectionBannerRepo;
    private final MainSectionMapper mainsectionMapper;
    private final ShopProductRepo shopProductRepo;

    /** mainsection 순서 변경*/
    @Transactional
    public void mainsectionOrder(long[] msIdxs) {
        for(int i=0; i<msIdxs.length -1; i++){
            MainSection mainSection = mainSectionRepo.getReferenceById(msIdxs[i]);
            mainSection.updateMsOrder(i+1);
        }
    }

    /** 메인섹션 삭제*/
    @Transactional
    public void mainsectionDelete(ParamMap paramMap) {
        MainSection mainSection = mainSectionRepo.findById(paramMap.getLong("msIdx")).orElseThrow(()-> new MsgException("존재하지 않는 메인섹션 입니다."));
        for(int i=0; i<mainSection.getMainSectionBanners().size();i++){
            mainSection.getMainSectionBanners().get(i).getMsbnIdx();
            mainSectionBannerRepo.deleteById(mainSection.getMainSectionBanners().get(i).getMsbnIdx());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("msspMsIdx", String.valueOf(mainSection.getMsIdx()));
        mainsectionMapper.deleteMainSectionSp(map);
        mainSectionRepo.delete(mainSection);
    }

    /** mainsection update*/
    @Transactional
    public void mainsectionUpdate(ParamMap paramMap, MultipartHttpServletRequest mfRequest) {
        MainSection mainSection = mainSectionRepo.findById(paramMap.getLong("msIdx")).orElseThrow(()-> new MsgException("존재하지 않는 메인섹션 입니다."));
        MainSection mainSectionParam = paramMap.mapParam(MainSection.class);

//        if(mainSection.getMsType().equals(MsType.PRODUCT)){
            try {
                String [] paramKeys = paramMap.keySet().toArray(new String[paramMap.keySet().size()]);
                String key;
                String mainSectionDesignKey;
                Map<String,Object> msJsonMap = new HashMap<>();
                for(int i=0;i<paramKeys.length;i++){
                    key = paramKeys[i];
                    for(int k=0;k<MsType.values().length;k++){
                        mainSectionDesignKey = String.valueOf(MsJson.values()[k]);
                        if(key.equals(mainSectionDesignKey)){
                            msJsonMap.put(key,paramMap.get(key));
                        }
                    }
                }

                ObjectMapper om = new ObjectMapper();
                om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                String msJson = om.writeValueAsString(msJsonMap);
                mainSection.setMsJson(msJson);
            } catch (JsonProcessingException e){
                System.out.println(e);
                throw new MsgException("잠시 후 다시 시도해주세요.");
            }
//        }

        mainSection.setMsName(mainSectionParam.getMsName());
        mainSection.setMsSubName(mainSectionParam.getMsSubName());
        mainSection.setMsType(mainSectionParam.getMsType());
        mainSection.setMsOrder(mainSection.getMsOrder()*mainSectionParam.getMsOrder());

        // 배너 제거 전 호출
        List<MainSectionBanner> mainSectionBanners = mainSection.getMainSectionBanners();

        // 배너 제거
        mainSection.setMainSectionBanners(new ArrayList<>());
        if(mainSection.getMsType().equals(MsType.BANNER)){
            List<String> msbnOrders = paramMap.getList("msbnOrder");
            List<MultipartFile> msbnWebFiles = mfRequest.getFiles("msbnWebFile");
            List<MultipartFile> msbnMobileFiles = mfRequest.getFiles("msbnMobileFile");
            List<String> msbnUrls = paramMap.getList("msbnUrl");
            msbnOrders.remove(msbnOrders.size()-1);
            int size = Math.max(mainSectionBanners.size(), msbnOrders.size());

            for(int i=0;i<size;i++){
                String webFile="";
                String webFileName="";
                String mobileFile ="";
                String mobileFileName ="";
                if(msbnOrders.size() > i){
                    // 노출순서
                    int order = i+1;
                    if(!msbnOrders.get(i).equals("") && msbnOrders.get(i) != null){
                        order = Integer.parseInt(msbnOrders.get(i));
                    }
                    if(mainSectionBanners.size() > i){
                        // update
                        MainSectionBanner msbn = mainSectionBanners.get(i);
                        msbn.setMsbnOrder(order);
                        msbn.setMsbnUrl(msbnUrls.get(i));

                        if(msbnWebFiles.get(i).isEmpty()){
                            msbn.setMsbnWebFile(msbn.getMsbnWebFile());
                            msbn.setMsbnWebFile(msbn.getMsbnWebFileName());
                        }else{
                            webFile = Common.uploadFilePath(msbnWebFiles.get(i), "mainsection/banner/web/", AdminBucket.OPEN);
                            webFileName = msbnWebFiles.get(i).getOriginalFilename();

                            msbn.setMsbnWebFile(webFile);
                            msbn.setMsbnWebFileName(webFileName);
                        }
                        if(msbnMobileFiles.get(i).isEmpty()){
                            msbn.setMsbnWebFile(msbn.getMsbnMobileFile());
                            msbn.setMsbnWebFile(msbn.getMsbnMobileFileName());
                        }else{
                            mobileFile = Common.uploadFilePath(msbnMobileFiles.get(i), "mainsection/banner/mobile/", AdminBucket.OPEN);
                            mobileFileName = msbnMobileFiles.get(i).getOriginalFilename();

                            msbn.setMsbnMobileFile(mobileFile);
                            msbn.setMsbnMobileFileName(mobileFileName);
                        }

                    }else{
                        // insert
                        MainSectionBanner msbn = MainSectionBanner.builder()
                                .msbnOrder(order)
                                .mainSection(mainSection)
                                .msbnUrl(paramMap.getList("msbnUrl").get(i)+"")
                                .build();

                        if(!msbnWebFiles.get(i).getOriginalFilename().equals("")){
                            webFile = Common.uploadFilePath(mfRequest.getFile("msbnWebFile"), "mainsection/banner/web/", AdminBucket.OPEN);
                            webFileName = mfRequest.getFile("msbnWebFile").getOriginalFilename();
                        }
                        if(!msbnMobileFiles.get(i).getOriginalFilename().equals("")){
                            mobileFile = Common.uploadFilePath(mfRequest.getFile("msbnMobileFile"), "mainsection/banner/mobile/", AdminBucket.OPEN);
                            mobileFileName = mfRequest.getFile("msbnMobileFile").getOriginalFilename();
                        }
                        msbn.setMsbnWebFile(webFile);
                        msbn.setMsbnWebFileName(webFileName);
                        msbn.setMsbnMobileFile(mobileFile);
                        msbn.setMsbnMobileFileName(mobileFileName);
                        mainSectionBannerRepo.save(msbn);
                    }
                }else{
                    // delete
                    MainSectionBanner msbn = mainSectionBanners.get(i);
                    mainSectionBannerRepo.delete(msbn);
                }
            }
        }else if(mainSection.getMsType().equals(MsType.PRODUCT)){
            // 상품 제거 전 조회
            List<Map<String,Object>> mainSectionSps = mainsectionMapper.selectMainSectionShopProductByMsIdx(mainSection.getMsIdx());

            // 상품 제거
            Map<String,Object> msspMap = new HashMap<>();
            msspMap.put("msspMsIdx", mainSection.getMsIdx());
            mainsectionMapper.deleteMainSectionSp(msspMap);

            //이후 다시 insert
            List<String> msspSpIdxs = paramMap.getList("spIdx");
            List<String> msspOrders = paramMap.getList("msspOrder");
            int size = Math.max(mainSectionSps.size(), msspSpIdxs.size());
            for(int i=0;i<size;i++){
                if(msspSpIdxs.size() > i){
                    if(mainSectionSps.size() > i){
                        Map<String,Object> map = new HashMap<>();
                        // update
                        Map<String,Object> mssp = mainSectionSps.get(i);
                        map.put("msspOrder", msspOrders.get(i));
                        map.put("msspMsIdx", mssp.get("MSSP_MS_IDX"));
                        map.put("msspSpIdx", mssp.get("MSSP_SP_IDX"));
                        mainsectionMapper.updateMainSectionSp(map);
                    }else{
                        List<String> spIdxs = paramMap.getList("spIdx");
                        ShopProduct shopProduct = shopProductRepo.getReferenceById(Long.parseLong(spIdxs.get(i)));
                        Map<String,Object> map = new HashMap<>();
                        map.put("msspOrder", msspOrders.get(i));
                        map.put("msspMsIdx", mainSection.getMsIdx());
                        map.put("msspSpIdx", shopProduct.getSpIdx());
                        mainsectionMapper.insertMainSectionSp(map);
                    }
                }else{
                    msspMap.put("msspSpIdx", msspSpIdxs.get(i));
                    mainsectionMapper.deleteMainSectionSp(msspMap);
                }
            }
        }else if(mainSection.getMsType().equals(MsType.BOTH)){
            List<String> msbnOrders = paramMap.getList("msbnOrder");
            List<String> msbnUrls = paramMap.getList("msbnUrl");
            int bannerSize = Math.max(mainSectionBanners.size(), msbnOrders.size()); // 현재 고정 6
            // 배너 행
            for(int i=0;i<bannerSize;i++){
                String webFile="";
                String webFileName="";
                String mobileFile ="";
                String mobileFileName ="";
                String bannerWebFile = "msbnWebFile"+(i+1);
                String bannerMobileFile = "msbnMobileFile"+(i+1);

                // 노출순서
                int order = i+1;
                if(!msbnOrders.get(i).equals("") && msbnOrders.get(i) != null){
                    order = Integer.parseInt(msbnOrders.get(i));
                }
                // update
                MainSectionBanner msbn = mainSectionBanners.get(i);
                msbn.setMsbnOrder(order);
                msbn.setMsbnUrl(msbnUrls.get(i));

                if(mfRequest.getFile(bannerWebFile).isEmpty()){
                    msbn.setMsbnWebFile(msbn.getMsbnWebFile());
                    msbn.setMsbnWebFile(msbn.getMsbnWebFileName());
                }else{
                    webFile = Common.uploadFilePath(mfRequest.getFile(bannerWebFile), "mainsection/banner/web/", AdminBucket.OPEN);
                    webFileName = mfRequest.getFile(bannerWebFile).getOriginalFilename();

                    msbn.setMsbnWebFile(webFile);
                    msbn.setMsbnWebFileName(webFileName);
                }
                if(mfRequest.getFile(bannerMobileFile).isEmpty()){
                    msbn.setMsbnWebFile(msbn.getMsbnMobileFile());
                    msbn.setMsbnWebFile(msbn.getMsbnMobileFileName());
                }else{
                    mobileFile = Common.uploadFilePath(mfRequest.getFile(bannerMobileFile), "mainsection/banner/mobile/", AdminBucket.OPEN);
                    mobileFileName = mfRequest.getFile(bannerMobileFile).getOriginalFilename();

                    msbn.setMsbnMobileFile(mobileFile);
                    msbn.setMsbnMobileFileName(mobileFileName);
                }

                // 상품
                // 상품 제거 전 호출
                Map<String,Object> shopBannerSearchMap = new HashMap<>();
                shopBannerSearchMap.put("msIdx", mainSection.getMsIdx());
                shopBannerSearchMap.put("msspOrder", i+1);
                List<Map<String,Object>> mainSectionSps = mainsectionMapper.selectMainSectionBannerShopProductByMsIdx(shopBannerSearchMap);

                // 상품 제거
                Map<String,Object> msspMap = new HashMap<>();
                msspMap.put("msspMsIdx", mainSection.getMsIdx());
                msspMap.put("msspOrder", i+1);
                mainsectionMapper.deleteMainSectionSp(msspMap);

                List<String> msspSpIdxs = paramMap.getList("spIdx"+(i+1));


                // 다 지우고 다시 insert
                if(msspSpIdxs.size() > 0){
                    for(int k=0;k<msspSpIdxs.size();k++){
                        //insert
                        ShopProduct shopProduct = shopProductRepo.getReferenceById(Long.parseLong(msspSpIdxs.get(k)));
                        Map<String,Object> map = new HashMap<>();
                        map.put("msspOrder", ((i+1)*100)+1);
                        map.put("msspMsIdx", mainSection.getMsIdx());
                        map.put("msspSpIdx", shopProduct.getSpIdx());
                        mainsectionMapper.insertMainSectionSp(map);
                    }
                }

//                int productSize = Math.max(mainSectionSps.size(), msspSpIdxs.size());
//                for(int k=0;k<productSize;k++){

//                    if(msspSpIdxs.size() > k){
//                        if(mainSectionSps.size() > k){
//                            Map<String,Object> map = new HashMap<>();
//                            // update
//                            System.out.println("update in~~~~~~~");
//                            Map<String,Object> mssp = mainSectionSps.get(k);
//                            map.put("msspOrder", ((i+1)*100)+1);
//                            map.put("msspMsIdx", mssp.get("MSSP_MS_IDX"));
//                            map.put("msspSpIdx", mssp.get("MSSP_SP_IDX"));
//                            mainsectionMapper.updateMainSectionSp(map);
//                        }else{
//                            System.out.println("insert in~~~~~~~");
//                            //insert
//                            ShopProduct shopProduct = shopProductRepo.getReferenceById(Long.parseLong(msspSpIdxs.get(k)));
//                            Map<String,Object> map = new HashMap<>();
//                            map.put("msspOrder", ((i+1)*100)+1);
//                            map.put("msspMsIdx", mainSection.getMsIdx());
//                            map.put("msspSpIdx", shopProduct.getSpIdx());
//                            mainsectionMapper.insertMainSectionSp(map);
//                        }
//                    }
//                    else{
//                        System.out.println("delete in~~~~~~~");
//                        //delete
//                        Map<String,Object> msspMap = new HashMap<>();
//                        msspMap.put("msspMsIdx", mainSection.getMsIdx());
//                        msspMap.put("msspOrder", i+1);
//                        msspMap.put("msspSpIdx", mainSectionSps.get(k).get("MSSP_SP_IDX"));
//                        mainsectionMapper.deleteMainSectionSp(msspMap);
//                    }
//                }
            }
        }
    }

    /** mainsection insert*/
    @Transactional
    public Long mainsectionSave(ParamMap paramMap, MultipartHttpServletRequest mfRequest) {
        MainSection mainSection = paramMap.mapParam(MainSection.class);
//        if(mainSection.getMsType().equals(MsType.PRODUCT)){
            try {
                String [] paramKeys = paramMap.keySet().toArray(new String[paramMap.keySet().size()]);
                String key;
                String mainSectionDesignKey;

                Map<String,Object> msJsonMap = new HashMap<>();
                for(int i=0;i<paramKeys.length;i++){
                    key = paramKeys[i];
                    for(int k=0;k<MsType.values().length;k++){
                        mainSectionDesignKey = String.valueOf(MsJson.values()[k]);
                        if(key.equals(mainSectionDesignKey)){
                            msJsonMap.put(key,paramMap.get(key));
                        }
                    }
                }

                ObjectMapper om = new ObjectMapper();
                om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                String msJson = om.writeValueAsString(msJsonMap);
                mainSection.setMsJson(msJson);
            } catch (JsonProcessingException e){
                System.out.println(e);
                throw new MsgException("잠시 후 다시 시도해주세요.");
            }
//        }

        int size = mainSectionRepo.findByShop_shopIdx(mainSection.getShop().getShopIdx()).size();
        mainSection.setMsOrder(mainSection.getMsOrder()*(size+1));

        mainSectionRepo.save(mainSection);

        if(mainSection.getMsType().equals(MsType.BANNER)){
            for(int i=1;i<paramMap.getList("msbnOrder").size();i++){
                String webFile="";
                String webFileName="";
                String mobileFile ="";
                String mobileFileName ="";
                MainSectionBanner msbn = MainSectionBanner.builder()
                        .msbnOrder(i)
                        .mainSection(mainSection)
                        .msbnUrl(paramMap.getList("msbnUrl").get(i)+"")
                        .build();

                if(!mfRequest.getFile("msbnWebFile").getOriginalFilename().equals("")){
                    webFile = Common.uploadFilePath(mfRequest.getFile("msbnWebFile"), "mainsection/banner/web/", AdminBucket.OPEN);
                    webFileName = mfRequest.getFile("msbnWebFile").getOriginalFilename();
                }
                if(!mfRequest.getFile("msbnMobileFile").getOriginalFilename().equals("")){
                    mobileFile = Common.uploadFilePath(mfRequest.getFile("msbnMobileFile"), "mainsection/banner/mobile/", AdminBucket.OPEN);
                    mobileFileName = mfRequest.getFile("msbnMobileFile").getOriginalFilename();
                }
                msbn.setMsbnWebFile(webFile);
                msbn.setMsbnWebFileName(webFileName);
                msbn.setMsbnMobileFile(mobileFile);
                msbn.setMsbnMobileFileName(mobileFileName);
                mainSectionBannerRepo.save(msbn);
            }
        }else if(mainSection.getMsType().equals(MsType.PRODUCT)){
            List<String> spIdxs = paramMap.getList("spIdx");
            List<String> msspOrders = paramMap.getList("msspOrder");
            for(int i=0;i<spIdxs.size(); i++){
                ShopProduct shopProduct = shopProductRepo.getReferenceById(Long.parseLong(spIdxs.get(i)));
                Map<String,Object> map = new HashMap<>();
                map.put("msspMsIdx", mainSection.getMsIdx());
                map.put("msspSpIdx", shopProduct.getSpIdx());
//                map.put("msspOrder", i+1);
                map.put("msspOrder", msspOrders.get(i));
                mainsectionMapper.insertMainSectionSp(map);
            }
        }else if(mainSection.getMsType().equals(MsType.BOTH)){
            for(int i=0;i<paramMap.getList("msbnOrder").size();i++){
                String webFile="";
                String webFileName="";
                String mobileFile ="";
                String mobileFileName ="";
                MainSectionBanner msbn = MainSectionBanner.builder()
                        .msbnOrder((i+1)*100)
                        .mainSection(mainSection)
                        .msbnUrl(paramMap.getList("msbnUrl").get(i)+"")
                        .build();
                String bannerWebFile = "msbnWebFile"+(i+1);
                String bannerMobileFile = "msbnMobileFile"+(i+1);
                if(!mfRequest.getFile(bannerWebFile).getOriginalFilename().equals("")){
                    webFile = Common.uploadFilePath(mfRequest.getFile(bannerWebFile), "mainsection/banner/web/", AdminBucket.OPEN);
                    webFileName = mfRequest.getFile(bannerWebFile).getOriginalFilename();
                }
                if(!mfRequest.getFile(bannerMobileFile).getOriginalFilename().equals("")){
                    mobileFile = Common.uploadFilePath(mfRequest.getFile(bannerMobileFile), "mainsection/banner/mobile/", AdminBucket.OPEN);
                    mobileFileName = mfRequest.getFile(bannerMobileFile).getOriginalFilename();
                }
                msbn.setMsbnWebFile(webFile);
                msbn.setMsbnWebFileName(webFileName);
                msbn.setMsbnMobileFile(mobileFile);
                msbn.setMsbnMobileFileName(mobileFileName);
                mainSectionBannerRepo.save(msbn);

                List<String> spIdxs = paramMap.getList("spIdx"+(i+1));
                for(int j=0;j<spIdxs.size(); j++){
                    ShopProduct shopProduct = shopProductRepo.getReferenceById(Long.parseLong(spIdxs.get(j)));
                    Map<String,Object> map = new HashMap<>();
                    map.put("msspMsIdx", mainSection.getMsIdx());
                    map.put("msspSpIdx", shopProduct.getSpIdx());
                    map.put("msspOrder", ((i+1)*100)+1);
                    mainsectionMapper.insertMainSectionSp(map);
                }
            }
        }

        return mainSection.getMsIdx();
    }

    /** mainsection 노출상태 토글 */
    @Transactional
    public void mainsectionDisplayToggle(ParamMap paramMap) {
        MainSection mainSection = mainSectionRepo.findById(paramMap.getLong("msIdx")).orElseThrow(()-> new MsgException("존재하지 않는 메인섹션 입니다."));
        mainSection.setMsOrder(mainSection.getMsOrder() * -1);
    }
}

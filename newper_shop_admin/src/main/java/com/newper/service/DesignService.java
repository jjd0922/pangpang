package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.FbType;
import com.newper.constant.etc.HoType;
import com.newper.constant.etc.ShopDesign;
import com.newper.dto.ParamMap;
import com.newper.entity.FloatingBar;
import com.newper.entity.HeaderOrder;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.repository.FloatingBarRepo;
import com.newper.repository.HeaderOrderRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DesignService {

    private final ShopRepo shopRepo;
    private final HeaderOrderRepo headerOrderRepo;
    private final FloatingBarRepo floatingBarRepo;

    /** 디자인 update*/
    @Transactional
    public void shopDesignUpdate(ParamMap paramMap) {
        Shop shop = shopRepo.findById(paramMap.getInt("shopIdx")).orElseThrow(()-> new MsgException("존재하지 않는 분양몰입니다."));

        Map<String,Object> designMap = shop.getShopDesign();
        for (ShopDesign shopDesign : ShopDesign.values()) {
            designMap.put(shopDesign.name(),paramMap.get(shopDesign.name()));
        }
    }

    /** 헤더 update*/
    @Transactional
    public void shopHeaderUpdate(ParamMap paramMap) {
        Shop shop = shopRepo.getReferenceById(paramMap.getInt("shopIdx"));
        Map<String,Object> designMap = new HashMap<>();
        for (ShopDesign shopDesign : ShopDesign.values()) {
            if(paramMap.containsKey(shopDesign.name())){
                designMap.put(shopDesign.name(),paramMap.get(shopDesign.name()));
            }
        }
        shop.getShopDesign().putAll(designMap);
        shop.setShopHdLoginGroup(paramMap.getList("shopHdLoginGroup"));

        for(int i=1;i<=3;i++){
            for(int k=1;k<=3;k++){
                HeaderOrder updateHo = headerOrderRepo.findByShopAndHoRowAndHoCol(shop, i,k).orElseGet(()-> {
                    return HeaderOrder.builder()
                            .shop(shop)
                            .build();
                });

                updateHo.setHoRow(i);
                updateHo.setHoCol(k);

                String hoType_value=paramMap.getString("headerOrder" + i + "_" + k);
                HoType hoType=HoType.NONE;

                if(StringUtils.hasText(hoType_value)){
                    hoType = HoType.valueOf(hoType_value);
                }
                updateHo.setHoType(hoType);

                headerOrderRepo.saveAndFlush(updateHo);
            }
        }
    }
    /** 플로팅바 저장*/
    @Transactional
    public void shopFloatingSave(ParamMap paramMap, MultipartHttpServletRequest mfRequest) {
        Shop shop = shopRepo.getReferenceById(paramMap.getInt("shopIdx"));

        Map<String,Object> designMap = new HashMap<>();
        for (ShopDesign shopDesign : ShopDesign.values()) {
            if(paramMap.containsKey(shopDesign.name())){
                designMap.put(shopDesign.name(),paramMap.get(shopDesign.name()));
            }
        }
        shop.getShopDesign().putAll(designMap);

        List<FloatingBar> fbList = shop.getFloatingBarList();
        //필수 플로팅바 정보 update. 기타는 list에 담음
        List<FloatingBar> etcList = new ArrayList<>();
        for (int i=0; i<fbList.size();i++) {
            String fbThumbnail="";
            String fbThumbnailMobile="";

            FloatingBar floatingBar = fbList.get(i);
            String fbType = floatingBar.getFbType().name();
            if (fbType.equals("RECENT")) {
                floatingBar.setFbDisplay(paramMap.getInt("recent"));
            }else if (fbType.equals("BASKET")) {
                floatingBar.setFbDisplay(paramMap.getInt("basket"));
            }else if (fbType.equals("TEL")) {
                int sangdam_use=paramMap.getInt("sangdam");
                int sangdam_order=paramMap.getInt("sangdam_order");
                if(mfRequest.getFile("sangdam_thumbnail").isEmpty()){
                    if(paramMap.get("sangdam_thumbnail") == null || paramMap.getString("sangdam_thumbnail").equals("")){
                        floatingBar.setFbThumbnail("/default/fb_tel.png");
                    }else{
//                        fbThumbnail = Common.uploadFilePath(mfRequest.getFile("sangdam_thumbnail"), "design/floating/web/", AdminBucket.OPEN);
//                        floatingBar.setFbThumbnail(fbThumbnail);
                    }
                }else {
                    fbThumbnail = Common.uploadFilePath(mfRequest.getFile("sangdam_thumbnail"), "design/floating/web/", AdminBucket.OPEN);
                    floatingBar.setFbThumbnail(fbThumbnail);
                }
                if(mfRequest.getFile("sangdam_thumbnail_mobile").isEmpty()){
                    if(paramMap.get("sangdam_thumbnail_mobile") == null || paramMap.getString("sangdam_thumbnail_mobile").equals("")){
                        floatingBar.setFbThumbnailMobile("/default/fb_tel.png");
                    }else{
//                        fbThumbnailMobile = Common.uploadFilePath(mfRequest.getFile("sangdam_thumbnail_mobile"), "design/floating/mobile/", AdminBucket.OPEN);
//                        floatingBar.setFbThumbnailMobile(fbThumbnailMobile);
                    }
                }else {
                    fbThumbnailMobile = Common.uploadFilePath(mfRequest.getFile("sangdam_thumbnail_mobile"), "design/floating/mobile/", AdminBucket.OPEN);
                    floatingBar.setFbThumbnailMobile(fbThumbnailMobile);
                }
                floatingBar.setFbDisplay(sangdam_order*sangdam_use);
                floatingBar.setFbUrl(paramMap.getString("sangdam_url"));

            }else if (fbType.equals("ETC")) {
                etcList.add(floatingBar);
            }
        }
        List add = paramMap.getList("add");
        List add_order = paramMap.getList("add_order");
        List<String> add_name = paramMap.getList("add_name");
        List<String> add_url = paramMap.getList("add_url");
        List<MultipartFile> add_thumbnail = mfRequest.getFiles("add_thumbnail");
        List<String> add_thumbnail_str = paramMap.getList("add_thumbnail");
        List<MultipartFile> add_thumbnail_mobile = mfRequest.getFiles("add_thumbnail_mobile");
        List<String> add_thumbnail_mobile_str = paramMap.getList("add_thumbnail_mobile");

        int size=Math.max(add.size(), etcList.size());
        for (int i = 0; i < size; i++) {
            String fbThumbnail="";
            String fbThumbnailMobile="";
            //추가할 fb 있는 경우
            if(add.size() > i ){
                int display=Integer.parseInt( add.get(i)+"");
                int order=Integer.parseInt( add_order.get(i)+"");
                if(etcList.size()>i){
                    //update
                    FloatingBar floatingBar = etcList.get(i);
                    floatingBar.setFbName(add_name.get(i));
                    floatingBar.setFbDisplay(display*order);
                    floatingBar.setFbUrl(add_url.get(i));
                    if(!add_thumbnail.isEmpty()){
                        if(add_thumbnail.get(i).isEmpty()){
                            if(add_thumbnail_str == null || add_thumbnail_str.get(i).equals("")){
                                floatingBar.setFbThumbnail("/default/fb_tel.png");
                            }else{
    //                            fbThumbnail = Common.uploadFilePath(add_thumbnail.get(i), "design/floating/web/", AdminBucket.OPEN);
    //                            floatingBar.setFbThumbnail(fbThumbnail);
                            }
                        }else{
                            fbThumbnail = Common.uploadFilePath(add_thumbnail.get(i), "design/floating/web/", AdminBucket.OPEN);
                            floatingBar.setFbThumbnail(fbThumbnail);
                        }
                    }
                    if(!add_thumbnail_mobile.isEmpty()){
                        if(add_thumbnail_mobile.get(i).isEmpty()){
                            if(add_thumbnail_mobile_str == null || add_thumbnail_mobile_str.get(i).equals("")){
                                floatingBar.setFbThumbnailMobile("/default/fb_tel.png");
                            }else{
    //                            fbThumbnailMobile = Common.uploadFilePath(add_thumbnail_mobile.get(i), "design/floating/mobile/", AdminBucket.OPEN);
    //                            floatingBar.setFbThumbnailMobile(fbThumbnailMobile);
                            }
                        }else{
                            fbThumbnailMobile = Common.uploadFilePath(add_thumbnail_mobile.get(i), "design/floating/mobile/", AdminBucket.OPEN);
                            floatingBar.setFbThumbnailMobile(fbThumbnailMobile);
                        }
                    }
                }else{
                    //insert
                    FloatingBar fb = FloatingBar.builder()
                            .fbName(add_name.get(i))
                            .fbDisplay( display*order )
                            .fbType(FbType.ETC)
                            .fbUrl(add_url.get(i))
                            .build();
                    if(!add_thumbnail.isEmpty()){
                        if(add_thumbnail.get(i).isEmpty()){
                            fb.setFbThumbnail("/default/fb_tel.png");
                        }else{
                            fbThumbnail = Common.uploadFilePath(add_thumbnail.get(i), "design/floating/web/", AdminBucket.OPEN);
                            fb.setFbThumbnail(fbThumbnail);
                        }
                    }
                    if(!add_thumbnail_mobile.isEmpty()){
                        if(add_thumbnail_mobile.get(i).isEmpty()){
                            fb.setFbThumbnailMobile("/default/fb_tel.png");
                        }else{
                            fbThumbnailMobile = Common.uploadFilePath(add_thumbnail_mobile.get(i), "design/floating/mobile/", AdminBucket.OPEN);
                            fb.setFbThumbnailMobile(fbThumbnailMobile);
                        }
                    }
                    shop.addFloatingBar(fb);
                }
            }else{
                //delete
                FloatingBar floatingBar = etcList.get(i);
                fbList.remove(floatingBar);
                floatingBarRepo.delete(floatingBar);
            }
        }
    }
}

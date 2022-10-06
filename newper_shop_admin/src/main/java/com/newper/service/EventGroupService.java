package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.EgJson;
import com.newper.dto.ParamMap;
import com.newper.entity.EventCategory;
import com.newper.entity.EventGroup;
import com.newper.entity.EventSp;
import com.newper.exception.MsgException;
import com.newper.repository.EventCategoryRepo;
import com.newper.repository.EventGroupRepo;
import com.newper.repository.EventSpRepo;
import com.newper.repository.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventGroupService {

    private final ShopProductRepo shopProductRepo;
    private final EventGroupRepo eventGroupRepo;
    private final EventCategoryRepo eventCategoryRepo;
    private final EventSpRepo eventSpRepo;
    /** 이벤트 그룹 update */
    @Transactional
    public void eventGroupUpdate(ParamMap paramMap, MultipartHttpServletRequest mfRequest) {

    }
    /** 이벤트 그룹 save */
    @Transactional
    public Long eventGroupSave(ParamMap paramMap, MultipartHttpServletRequest mfRequest) {
        System.out.println("in~~~~~~~~~~");
        EventGroup eventGroup = paramMap.mapParam(EventGroup.class);

        String EG_THUMBNAIL_WEB = "";
        String EG_THUMBNAIL_MOBILE = "";
        if(!mfRequest.getFile("EG_THUMBNAIL_WEB").isEmpty()){
            EG_THUMBNAIL_WEB = Common.uploadFilePath(mfRequest.getFile("EG_THUMBNAIL_WEB"),"board/event/web/", AdminBucket.OPEN);
        }else{
            throw new MsgException("썸네일(WEB) 파일을 등록해주세요.");
        }
        if(!mfRequest.getFile("EG_THUMBNAIL_MOBILE").isEmpty()){
            EG_THUMBNAIL_MOBILE = Common.uploadFilePath(mfRequest.getFile("EG_THUMBNAIL_MOBILE"),"board/event/mobile/", AdminBucket.OPEN);
        }

        Map<String,Object> jsonMap = new HashMap<>();
        paramMap.put("EG_THUMBNAIL_WEB", EG_THUMBNAIL_WEB);
        paramMap.put("EG_THUMBNAIL_MOBILE", EG_THUMBNAIL_MOBILE);
        for(EgJson egJson : EgJson.values()){
            jsonMap.put(egJson.name(), paramMap.get(egJson.name()));
        }

        if(paramMap.getString("egOpenDate").equals("")){
            throw new MsgException("노출 시작일을 선택해주세요.");
        }
        if(paramMap.getString("egOpenTime").equals("")){
            throw new MsgException("노출 시작시간을 선택해주세요.");
        }
        if(paramMap.getString("egCloseDate").equals("")){
            throw new MsgException("노출 종료일을 선택해주세요.");
        }
        if(paramMap.getString("egCloseTime").equals("")){
            throw new MsgException("노출 종료시간을 선택해주세요.");
        }
        LocalDate egOpenDate = LocalDate.parse(paramMap.getString("egOpenDate").replaceAll("[^0-9]", ""), DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalTime egOpenTime = LocalTime.parse(paramMap.getString("egOpenTime").replaceAll("[^0-9]",""), DateTimeFormatter.ofPattern("HHmm"));
        LocalDate egCloseDate = LocalDate.parse(paramMap.getString("egCloseDate").replaceAll("[^0-9]", ""), DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalTime egCloseTime = LocalTime.parse(paramMap.getString("egCloseTime").replaceAll("[^0-9]",""), DateTimeFormatter.ofPattern("HHmm"));

        eventGroup.setEgOpenDate(egOpenDate);
        eventGroup.setEgOpenTime(egOpenTime);
        eventGroup.setEgCloseDate(egCloseDate);
        eventGroup.setEgCloseTime(egCloseTime);
        eventGroup.setEgJson(jsonMap);
        eventGroupRepo.saveAndFlush(eventGroup);

        List<String> ecTitleList = paramMap.getList("ecTitle");
        for(int i=0;i<ecTitleList.size();i++){
            EventCategory eventCategory = EventCategory.builder()
                    .eventGroup(eventGroup)
                    .ecTitle(ecTitleList.get(i))
                    .ecOrder(i+1)
                    .build();
            eventCategoryRepo.saveAndFlush(eventCategory);

            List<String> spIdxs = paramMap.getList("spIdx"+(i+1));
            for(int k=0;k<spIdxs.size();k++){
                EventSp eventSp = EventSp.builder()
                        .eventCategory(eventCategory)
                        .shopProduct(shopProductRepo.getReferenceById(Long.parseLong(spIdxs.get(k))))
                        .espOrder(k+1)
                        .build();
                eventSpRepo.saveAndFlush(eventSp);
            }
        }

        return eventGroup.getEgIdx();
    }
    /** 이벤트그룹 노출/미노출 토글 */
    @Transactional
    public void toggleEventGroup(ParamMap paramMap) {
        EventGroup eventGroup = eventGroupRepo.findById(paramMap.getLong("egIdx")).orElseThrow(()-> new MsgException("존재하지 않는 이벤트 그룹입니다."));
        if(eventGroup.isEgState()){
            eventGroup.setEgState(false);
        }else{
            eventGroup.setEgState(true);
        }
    }

    @Transactional
    public void deleteEventGroup(ParamMap paramMap) {
        List<Long> egIdxs = paramMap.getListLong("egIdxs[]");

        eventGroupRepo.deleteAllById(egIdxs);
    }
}

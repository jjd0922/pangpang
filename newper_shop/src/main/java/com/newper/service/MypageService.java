package com.newper.service;


import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.ShopSession;
import com.newper.constant.AsState;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.repository.AfterServiceRepo;
import com.newper.repository.OrderGsRepo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageService {

    private final AfterServiceRepo afterServiceRepo;

    private final OrderGsRepo orderGsRepo;

    private ShopSession shopSession;


    /**
     * AS 등록 --> 뉴퍼마켓 구매제품
     **/
    @Transactional
    public long createAS(ParamMap paramMap, MultipartHttpServletRequest AS_FILE) {
        AfterService as = paramMap.mapParam(AfterService.class);

        try {
            String as_memo = paramMap.getString("AS_MEMO");
            as.setAsMemo(as_memo);
        } catch (NumberFormatException nfe) {
            throw new MsgException("내용을 입력해주세요");
        }

        try {
            String as_mail = paramMap.getString("AS_MAIL1");
            String as_mail2 = paramMap.getString("AS_MAIL2");
            as.setAsMail(as_mail + "@" + as_mail2);
        } catch (NumberFormatException nfe) {
            throw new MsgException("메일주소를 입력해주세요");
        }

        try {
            String as_phone1 = paramMap.getString("AS_PHONE1");
            String as_phone2 = paramMap.getString("AS_PHONE2");
            String as_phone3 = paramMap.getString("AS_PHONE3");
            as.setAsPhone(as_phone1 + as_phone2 + as_phone3);
        } catch (NumberFormatException nfe) {
            throw new MsgException("휴대폰번호를 입력해주세요");
        }

        try {
            long as_og_idx = paramMap.getLong("AS_OG_IDX");
            as.setOrderGs(orderGsRepo.getReferenceById(as_og_idx));
        } catch (NumberFormatException nfe) {
            throw new MsgException("상품을 선택해주세요.");
        }

        String as_name = paramMap.getString("AS_NAME");
        if (as_name == null) {
            as.setAsName("");
        } else if (as_name != null) {
            as.setAsName(as_name);
        }

//        as.setOrderGs(null);
        as.setDeliveryNum(null);
        as.setAsState(AsState.REQUEST);
        as.setAsDate(LocalDate.now());
        as.setAsTime(LocalTime.now());

        System.out.println("AS_FILE!!!!!!!!!!!!!!!!!!! = " + AS_FILE);


        List<MultipartFile> aa = AS_FILE.getFiles("AS_FILE");
        System.out.println("파일" + AS_FILE.getFiles("AS_FILE"));

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        StringBuffer sb = new StringBuffer();
        int cnt = AS_FILE.getFiles("AS_FILE").size();
        for (int i = 0; i < 10; i++) {
            JSONArray jsonArray2 = new JSONArray();
            String file = "";
            String fileName = "";
            if(i<cnt){
                file = Common.uploadFilePath(AS_FILE.getFiles("AS_FILE").get(i), "mypage/as_image/", AdminBucket.SECRET);
                fileName= AS_FILE.getFiles("AS_FILE").get(i).getOriginalFilename();
                jsonArray2.add(file);
                jsonArray2.add(fileName);
            }
            jsonArray.add(jsonArray2);

        }
        jsonObject.put("files",jsonArray);

        as.setAsFile(jsonObject);

////
////         파일확장자 확인
//        String asFileExtension = FilenameUtils.getExtension(AS_FILE.getOriginalFilename());
//
//        if (!asFileExtension.equals("jpg") && !asFileExtension.equals("gif") && !asFileExtension.equals("png")) {
//            throw new MsgException("사진은 png,gif,png 형식으로만 업로드 해주세요.");
//
//        }
//
//            String asFilePath = Common.uploadFilePath(AS_FILE, "mypage/as_image/", AdminBucket.SECRET);
//
//
//            as.setAsFile(asFilePath);
        //as.setAsFileName(AS_FILE.getOriginalFilename());
//            as.setAsFile("");
//
/*        String[] idxs = paramMap.getString("AS_FILE").split(",");

        int cnt =0;
        for(int i=0; i<idxs.length; i++){
            try {

                String file = "";
                String fileName = "";

                AfterService afterService = paramMap.mapParam(AfterService.class);
                if(AS_FILE.getSize()!=0){
                    file = Common.uploadFilePath(aa, "mypage/as_image/", AdminBucket.SECRET);
                    fileName= AS_FILE.getOriginalFilename();
                }

                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();

                //파일 추가시 파일 갯수만큼 반복문 적용
                for(int y=0; y<1; y++){
                    JSONArray jsonArray2 = new JSONArray();
                    jsonArray2.add(file);
                    jsonArray2.add(fileName);
                    jsonArray.add(jsonArray2);
                }
                jsonObject.put("files",jsonArray);

                as.setAsFile(jsonObject);

                afterServiceRepo.save(as);
                cnt++;
            }catch (Exception e){
                continue;
            }
            return cnt;
        }*/
        afterServiceRepo.save(as);
        return as.getAsIdx();

    }
}






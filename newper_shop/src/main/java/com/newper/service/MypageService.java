package com.newper.service;


import ch.qos.logback.core.joran.conditional.ElseAction;
import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.repository.ASRepo;
import com.newper.repository.OrderGsRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageService {

    private final ASRepo asRepo;

    private final OrderGsRepo orderGsRepo;

    private ShopSession shopSession;


    /**
     * AS 등록 --> 뉴퍼마켓 구매제품
     **/
    @Transactional
    public long createAS(ParamMap paramMap, MultipartFile AS_FILE) {
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

        as.setOrderGs(null);
        as.setDeliveryNum(null);

        String as_name = paramMap.getString("AS_NAME");
        if (as_name == null) {
            as.setAsName("");
        } else if (as_name != null) {
            as.setAsName(as_name);
        }

//
//         파일확장자 확인
        String asFileExtension = FilenameUtils.getExtension(AS_FILE.getOriginalFilename());

        if (!asFileExtension.equals("jpg") && !asFileExtension.equals("gif") && !asFileExtension.equals("png")) {
            throw new MsgException("사진은 png,gif,png 형식으로만 업로드 해주세요.");

        }

            String asFilePath = Common.uploadFilePath(AS_FILE, "mypage/as_image/", AdminBucket.SECRET);


            as.setAsFile(asFilePath);
            as.setAsFileName(AS_FILE.getOriginalFilename());
//            as.setAsFile("");


        as.setAsState("");
        as.setAsDate(LocalDate.now());
        as.setAsTime(LocalTime.now());


        asRepo.save(as);

        return as.getAsIdx();

    }


}



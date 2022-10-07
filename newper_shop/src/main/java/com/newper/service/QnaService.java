package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.entity.Customer;
import com.newper.entity.Qna;
import com.newper.entity.QnaSp;
import com.newper.exception.MsgException;
import com.newper.repository.CustomerRepo;
import com.newper.repository.QnaRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class QnaService {

    @Autowired
    private ShopSession shopSession;

    private final CustomerRepo customerRepo;
    private final QnaRepo qnaRepo;

    /** 1:1 문의 등록 */
    @Transactional
    public void saveQna(ParamMap paramMap, MultipartFile[] files) {
        Qna qna = paramMap.mapParam(Qna.class);
        Customer customer = customerRepo.getReferenceByCuId(shopSession.getId());
        qna.setCustomer(customer);

        // qna_ogg_idx set해야함 (있는경우)

        // qnaMail
        String mail = "";
        if (StringUtils.hasText(paramMap.getString("email"))) {
            String pattern = "\\w+\\.\\w+(\\.\\w+)?";
            if (!Pattern.matches(pattern, paramMap.getString("domain"))) {
                throw new MsgException("정확한 이메일을 입력해 주세요");
            }
            mail = paramMap.get("email") + "@" + paramMap.get("domain");
        }
        if (mail.equals("") && qna.isQnaMailAlarm()) {
            throw new MsgException("답변알림을 받으시려면 이메일주소를 입력해주세요.");
        }
        qna.setQnaMail(mail);
        // qnaPhone
        String phone = paramMap.getString("phone1") + paramMap.get("phone2") + paramMap.get("phone3");
        if (phone.equals("") && qna.isQnaPhoneAlarm()) {
            throw new MsgException("답변알림을 받으시려면 휴대폰 번호를 입력해 주세요.");
        }
        qna.setQnaPhone(phone);
        // qnaJson
        List<String> photoList = new ArrayList<>();
        String[] acceptArray = {"jpg","gif"};
        for (MultipartFile file : files) {
            if (file.isEmpty()) { // 파일 없을 시 반복문 종료
                break;
            }
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            boolean isAcceptable = Arrays.stream(acceptArray).anyMatch(element -> element.equals(extension));
            if (!isAcceptable) {
                throw new MsgException("jpg, gif 형식 파일만 등록할 수 있습니다.");
            }
            String photo = Common.uploadFilePath(file, "qna/", AdminBucket.SECRET);
            photoList.add(photo);
        }
        qna.setQnaJson(photoList);

        qnaRepo.save(qna);
    }

    /**상품문의 등록*/
    @Transactional
    public void saveQnaSp(ParamMap paramMap) {
        QnaSp qnaSp = paramMap.mapParam(QnaSp.class);
        Customer customer = customerRepo.getReferenceByCuId(shopSession.getId());
        if (customer != null) {
            qnaSp.setCustomer(customer);
        }
    }
}

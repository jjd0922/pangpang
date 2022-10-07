package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.entity.Customer;
import com.newper.entity.Qna;
import com.newper.exception.MsgException;
import com.newper.repository.CustomerRepo;
import com.newper.repository.QnaRepo;
import com.newper.repository.QnaSpRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    @Autowired
    private ShopSession shopSession;

    private final CustomerRepo customerRepo;
    private final QnaRepo qnaRepo;
    private final QnaSpRepo qnaSpRepo;

    /** 1:1 문의 등록 */
    @Transactional
    public void saveQna(ParamMap paramMap, MultipartFile[] files) {
        Qna qna = paramMap.mapParam(Qna.class);
        Customer customer = customerRepo.getReferenceByCuId(shopSession.getId());
        qna.setCustomer(customer);

        // qna_ogg_idx set해야함 (있는경우)

        // qnaMail, qnaPhone setting
        qna.setParam(paramMap.getMap());

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
            String photo = Common.uploadFilePath(file, "qna/", AdminBucket.OPEN);
            photoList.add(photo);
        }
        qna.setQnaJson(photoList);

        qnaRepo.save(qna);
    }

    /** 1:1문의 수정 처리 */
    @Transactional
    public void updateQna(Long qnaIdx, ParamMap paramMap, MultipartFile[] files) {
        Qna qna = qnaRepo.findById(qnaIdx).orElseThrow(() -> new MsgException("존재하지 않는 1:1문의입니다."));
        Qna newQna = paramMap.mapParam(Qna.class);
        qna.updateQna(newQna);

        // qnaMail, qnaPhone setting
        qna.setParam(paramMap.getMap());

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
            String photo = Common.uploadFilePath(file, "qna/", AdminBucket.OPEN);
            photoList.add(photo);
        }
        qna.setQnaJson(photoList);
    }

    @Transactional
    public void deleteQna(Long qnaIdx) {
        qnaRepo.deleteById(qnaIdx);
    }

    @Transactional
    public void updateQsp(Long qspIdx, ParamMap paramMap) {

    }

    @Transactional
    public void deleteQsp(Long qspIdx) {
        qnaSpRepo.deleteById(qspIdx);
    }
}

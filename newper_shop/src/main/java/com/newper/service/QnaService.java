package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.entity.Customer;
import com.newper.entity.Qna;
import com.newper.repository.CustomerRepo;
import com.newper.repository.QnaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

        List<String> photoList = new ArrayList<>();
        for (MultipartFile file : files) {
            String photo = Common.uploadFilePath(file, "qna/", AdminBucket.SECRET);
            photoList.add(photo);
        }
        qna.setQnaJson(photoList);

        qnaRepo.save(qna);
    }
}

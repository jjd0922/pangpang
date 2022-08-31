package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.ConsultationResult;
import com.newper.exception.MsgException;
import com.newper.repository.ConsultationResultRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultationResultService {

    private  final ConsultationResultRepo consultationResultRepo;


    /**상담결과 저장*/
    @Transactional
    public Integer saveCounsel(ParamMap paramMap) {
        ConsultationResult consultationResult = paramMap.mapParam(ConsultationResult.class);

        try {
            String cr_type = paramMap.getString("CR_TYPE");
            consultationResult.setCrType(cr_type);
        } catch (NumberFormatException nfe) {
            throw new MsgException("상태를 체크해주세요");
        }
        try {
            String cr_title = paramMap.getString("CR_TITLE");
            consultationResult.setCrTitle(cr_title);
        } catch (NumberFormatException nfe) {
            throw new MsgException("제목을 입력해주세요");
        }
        try {
            String cr_content = paramMap.getString("CR_CONTENT");
            consultationResult.setCrContent(cr_content);
        } catch (NumberFormatException nfe) {
            throw new MsgException("내용을 입력해주세요");
        }

        consultationResultRepo.save(consultationResult);
        return consultationResult.getCrIdx();
    }

}
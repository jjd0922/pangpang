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
            String cr_memo = paramMap.getString("CR_MEMO");
            consultationResult.setCrMemo(cr_memo);
        } catch (NumberFormatException nfe) {
            throw new MsgException("내용을 입력해주세요");
        }

        consultationResultRepo.save(consultationResult);
        return consultationResult.getCrIdx();
    }

    /**상담결과 삭제*/
    @Transactional
    public void deletecounsel(Integer cr_idx){
        consultationResultRepo.deleteById(cr_idx);
    }

    /**
     * 상담결과 템플릿 업데이트
     */
    @Transactional
    public Integer updateCounsel(ParamMap paramMap, int crIdx) {

/*       ConsultationResult consultationResult = paramMap.mapParam(ConsultationResult.class);

        System.out.println("crIdx : " + consultationResult.getCrIdx());

        consultationResult.setCrIdx(consultationResult.getCrIdx());
        consultationResult.setCrType(consultationResult.getCrType());

        consultationResultRepo.save(consultationResult);

        return consultationResult.getCrIdx();*/
        ConsultationResult ori = consultationResultRepo.findById(crIdx).get();
        ConsultationResult consultationResult = paramMap.mapParam(ConsultationResult.class);
        System.out.println("type : " +ori.getCrType());
        System.out.println("idx : " +ori.getCrIdx());

        ori.setCrTitle(consultationResult.getCrTitle());
        ori.setCrMemo(consultationResult.getCrMemo());
        ori.setCrScript(consultationResult.getCrScript());


        consultationResultRepo.save(ori);
        return ori.getCrIdx();
    }

}
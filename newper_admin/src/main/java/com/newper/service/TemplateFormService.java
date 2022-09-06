package com.newper.service;

import com.newper.constant.TfType;
import com.newper.dto.ParamMap;
import com.newper.entity.Auth;
import com.newper.entity.Company;
import com.newper.entity.TemplateForm;
import com.newper.entity.User;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.repository.TemplateFormRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TemplateFormService {
    private final TemplateFormRepo templateFormRepo;

    /**
     * 문자템플릿 저장
     */
    @Transactional
    public Integer saveTemplate(ParamMap paramMap) {
        TemplateForm templateForm = paramMap.mapParam(TemplateForm.class);

        try {
            String tf_title = paramMap.getString("TF_TITLE");
            templateForm.setTfTitle(tf_title);
        } catch (NumberFormatException nfe) {
            throw new MsgException("제목을 입력해주세요");
        }
        try {
            String tf_content = paramMap.getString("TF_CONTENT");
            templateForm.setTfContent(tf_content);
        } catch (NumberFormatException nfe) {
            throw new MsgException("내용을 입력해주세요");
        }

        templateFormRepo.save(templateForm);
        return templateForm.getTfIdx();
    }


    /**
     * kakao/sms 템플릿 삭제
     */
    @Transactional
    public void deleteTemplate(Integer tf_idx) {

        templateFormRepo.deleteById(tf_idx);
    }


    /**
     * Sms템플릿 업데이트
     */
    @Transactional
    public Integer updateSms(ParamMap paramMap,int tfIdx) {

        TemplateForm ori = templateFormRepo.findById(tfIdx).get();
        TemplateForm templateForm = paramMap.mapParam(TemplateForm.class);
        System.out.println("type : " +ori.getTfType());

        ori.setTfTitle(templateForm.getTfTitle());
        ori.setTfContent(templateForm.getTfContent());


        templateFormRepo.save(ori);
        return ori.getTfIdx();
    }

    /**
     * Kakao템플릿 업데이트
     */
    @Transactional
    public Integer updateKakao(ParamMap paramMap, int tfIdx) {

        TemplateForm ori = templateFormRepo.findById(tfIdx).get();
        TemplateForm templateForm = paramMap.mapParam(TemplateForm.class);
        System.out.println("type : " +ori.getTfType());

        ori.setTfTitle(templateForm.getTfTitle());
        ori.setTfContent(templateForm.getTfContent());

//        System.out.println("tfIdx : " + templateForm.getTfIdx());
//
//        templateForm.setTfType(templateForm.getTfType());

        templateFormRepo.save(ori);
        return ori.getTfIdx();
    }
}

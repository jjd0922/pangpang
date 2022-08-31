package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.TemplateForm;
import com.newper.exception.MsgException;
import com.newper.repository.TemplateFormRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TemplateFormService {
    private final TemplateFormRepo templateFormRepo;

    /**문자템플릿 저장*/
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

}

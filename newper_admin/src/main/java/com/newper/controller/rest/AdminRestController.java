package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.ConsultationResultMapper;
import com.newper.mapper.TemplateFormMapper;
import com.newper.service.ConsultationResultService;
import com.newper.service.TemplateFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/admin/")
@RestController
@RequiredArgsConstructor
public class AdminRestController {
    private final TemplateFormMapper templateFormMapper;
    
    private  final TemplateFormService templateFormService;

    private  final ConsultationResultService consultationResultService;

    private  final ConsultationResultMapper consultationResultMapper;


    /**문자템플릿 관리 페이지*/
    @PostMapping("sms.dataTable")
    public ReturnDatatable sms(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("sms템플릿");
        System.out.println(paramMap.getMap());
        rd.setData(templateFormMapper.selectTemplateFormDatatable(paramMap.getMap()));
        rd.setRecordsTotal(templateFormMapper.countTemplateFormDatatable(paramMap.getMap()));
        return rd;
    }
    /**문자템플릿 저장*/
    @PostMapping(value = "createSmsTemplate.ajax")
    public ReturnMap smstemplate(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        int idx = templateFormService.saveTemplate(paramMap);

        rm.setMessage("등록이 완료되었습니다.");

        return rm;
    }
    /**카카오템플릿 관리 페이지*/
    @PostMapping("kakao.dataTable")
    public ReturnDatatable kakao(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("카카오템플릿");

        rd.setData(templateFormMapper.selectTemplateFormDatatable(paramMap.getMap()));
        rd.setRecordsTotal(templateFormMapper.countTemplateFormDatatable(paramMap.getMap()));
        return rd;
    }
    /**카카오템플릿 저장*/
    @PostMapping(value = "createKakaoTemplate.ajax")
    public ReturnMap kakaotemplate(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        int idx = templateFormService.saveTemplate(paramMap);

        rm.setMessage("등록이 완료되었습니다.");

        return rm;
    }

    /**카카오템플릿 삭제*/
    @PostMapping(value = "deletekakao.ajax")
    /*public ReturnMap deletekakao(ParamMap paramMap) {*/
/*        ReturnMap rm = new ReturnMap();

        paramMap.put("tfIdx", paramMap.getList("tfIdxList[]"));
        templateFormMapper.deleteTemplate(paramMap.getMap());
        rm.setMessage("삭제완료");
        return rm;
    }*/
    public ReturnMap deletekakao (Integer tf_idx) {
        ReturnMap rm = new ReturnMap();

        templateFormService.deleteTemplate(tf_idx);
        rm.setMessage("삭제 완료");

        return rm;

    }

    /**상담결과 관리 페이지*/
    @PostMapping("counsel.dataTable")
    public ReturnDatatable counssel(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("상담결과");

        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));
        return rd;
    }

    /**상담결과 저장*/
    @PostMapping(value = "createCounsel.ajax")
    public ReturnMap counselInsert(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        int idx = consultationResultService.saveCounsel(paramMap);

        rm.setMessage("등록이 완료되었습니다.");

        return rm;
    }
}

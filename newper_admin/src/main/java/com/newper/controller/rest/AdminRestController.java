package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.TemplateFormMapper;
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


    /**문자템플릿 관리 페이지*/
    @PostMapping("sms.dataTable")
    public ReturnDatatable sms(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("sms템플릿");

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

}

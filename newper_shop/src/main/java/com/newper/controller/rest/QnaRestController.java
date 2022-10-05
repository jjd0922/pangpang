package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna/")
public class QnaRestController {

    private final QnaService qnaService;

    /** 1:1 문의 등록 ajax */
    @PostMapping("save.ajax")
    public ReturnMap saveQna(ParamMap paramMap, MultipartFile[] photos) {
        ReturnMap rm = new ReturnMap();
        qnaService.saveQna(paramMap, photos);
        return rm;
    }


}

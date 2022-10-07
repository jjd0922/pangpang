package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
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
        rm.setMessage("1:1문의 등록 완료");
        return rm;
    }

    @PostMapping("update/{qnaIdx}.ajax")
    public ReturnMap updateQna(@PathVariable("qnaIdx") Long qnaIdx, ParamMap paramMap, MultipartFile[] photos) {
        ReturnMap rm = new ReturnMap();
        qnaService.updateQna(qnaIdx, paramMap, photos);
        return rm;
    }
    
    @PostMapping("delete/{qnaIdx}.ajax")
    public ReturnMap deleteQna(@PathVariable("qnaIdx") Long qnaIdx) {
        ReturnMap rm = new ReturnMap();
        qnaService.deleteQna(qnaIdx);
        return rm;
    }

    @PostMapping("sp/update/{qspIdx}.ajax")
    public ReturnMap updateQsp(@PathVariable("qspIdx") Long qspIdx, ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        qnaService.updateQsp(qspIdx, paramMap);
        return rm;
    }

    @PostMapping("sp/delete/{qspIdx}.ajax")
    public ReturnMap deleteQsp(@PathVariable("qspIdx") Long qspIdx) {
        ReturnMap rm = new ReturnMap();
        qnaService.deleteQsp(qspIdx);
        return rm;
    }


}

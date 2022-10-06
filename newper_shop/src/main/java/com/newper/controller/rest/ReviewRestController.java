package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review/")
public class ReviewRestController {

    private final ReviewService reviewService;

    @PostMapping("save.ajax")
    public ReturnMap saveReview(ParamMap paramMap, MultipartFile[] photos) {
        ReturnMap rm = new ReturnMap();
        reviewService.saveReview(paramMap, photos);
        rm.setMessage("리뷰 등록 완료");
        return rm;
    }

    @PostMapping("update/{rIdx}.ajax")
    public ReturnMap updateReview(@PathVariable("rIdx") Long rIdx, ParamMap paramMap, MultipartFile[] photos) {
        ReturnMap rm = new ReturnMap();
        reviewService.updateReview(rIdx, paramMap, photos);
        rm.setMessage("리뷰 수정 완료");
        return rm;
    }

    @PostMapping("delete/{rIdx}.ajax")
    public ReturnMap deleteReview(@PathVariable("rIdx") Long rIdx) {
        ReturnMap rm = new ReturnMap();
        reviewService.deleteReview(rIdx);
        return rm;
    }
}
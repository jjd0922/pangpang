package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.service.ReviewService;
import lombok.RequiredArgsConstructor;
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
    public ReturnMap spReviewSave(ParamMap paramMap, MultipartFile[] photos) {
        ReturnMap rm = new ReturnMap();
        reviewService.saveReview(paramMap, photos);
        return rm;
    }
}
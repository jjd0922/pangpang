package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.entity.Customer;
import com.newper.entity.Review;
import com.newper.exception.MsgException;
import com.newper.mapper.OrdersMapper;
import com.newper.repository.CustomerRepo;
import com.newper.repository.OrdersRepo;
import com.newper.repository.ReviewRepo;
import com.newper.repository.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    @Autowired
    private ShopSession shopSession;
    private final ReviewRepo reviewRepo;
    private final CustomerRepo customerRepo;
    private final OrdersMapper ordersMapper;
    private final OrdersRepo ordersRepo;
    private final ShopProductRepo shopProductRepo;
    /**상품리뷰등록*/
    @Transactional
    public Review saveReview(ParamMap paramMap, MultipartFile[] mfs) {
        if (mfs.length > 10) {
            throw new MsgException("최대 10개의 이미지 등록이 가능합니다.");
        }
        Review review = paramMap.mapParam(Review.class);
        Customer customer = customerRepo.getReferenceByCuId(shopSession.getId());
        if (customer == null) {
            throw new MsgException("로그인이 필요합니다.");
        }
        review.setCustomer(customer);

        Map<String, Object> map = ordersMapper.selectOrderAndShopByOggIdx(paramMap.getLong("oggIdx"));
        review.setOrders(ordersRepo.getReferenceById(Long.parseLong(map.get("O_IDX") + "")));
        review.setShopProduct(shopProductRepo.getReferenceById(Long.parseLong(map.get("SP_IDX")+"")));

        List<String> fileList = new ArrayList<>();
        String[] acceptArray = {"jpg","jpeg","png","gif"};
        for (MultipartFile mf : mfs) {
            if (mf.isEmpty()) { // 파일 없을 시 반복문 종료
                break;
            }
            String extension = FilenameUtils.getExtension(mf.getOriginalFilename());
            boolean isAcceptable = Arrays.stream(acceptArray).anyMatch(element -> element.equals(extension));
            if (!isAcceptable) {
                throw new MsgException("jpg, gif, png 형식 파일만 등록할 수 있습니다.");
            }
            String file = Common.uploadFilePath(mf, "shop_product/review/", AdminBucket.OPEN);
            fileList.add(file);
        }
        review.setRJson(fileList);
        reviewRepo.save(review);

        // 포인트 적립처리 필요
        return review;
    }

    /**리뷰 수정*/
    @Transactional
    public void updateReview(Long rIdx, ParamMap paramMap, MultipartFile[] mfs) {
        Review review = reviewRepo.findById(rIdx).orElseThrow(() -> new MsgException("존재하지 않는 리뷰입니다."));
        Review newReview = paramMap.mapParam(Review.class);
        review.updateReview(newReview);

        List<String> rJson = review.getRJson();
        String[] acceptArray = {"jpg","jpeg","png","gif"};
        for (MultipartFile mf : mfs) {
            if (mf.isEmpty()) { // 파일 없을 시 반복문 종료
                break;
            }
            String extension = FilenameUtils.getExtension(mf.getOriginalFilename());
            boolean isAcceptable = Arrays.stream(acceptArray).anyMatch(element -> element.equals(extension));
            if (!isAcceptable) {
                throw new MsgException("jpg, gif, png 형식 파일만 등록할 수 있습니다.");
            }
            String file = Common.uploadFilePath(mf, "shop_product/review/", AdminBucket.OPEN);
            rJson.add(file);
        }
        review.setRJson(rJson);
    }

    /**리뷰 삭제*/
    @Transactional
    public void deleteReview(Long rIdx) {
        reviewRepo.deleteById(rIdx);
    }
}
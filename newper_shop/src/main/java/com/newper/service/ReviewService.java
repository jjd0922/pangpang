package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.entity.Customer;
import com.newper.entity.Review;
import com.newper.mapper.OrdersMapper;
import com.newper.repository.CustomerRepo;
import com.newper.repository.OrdersRepo;
import com.newper.repository.ReviewRepo;
import com.newper.repository.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    public void saveReview(ParamMap paramMap, MultipartFile[] mfs) {
        Review review = paramMap.mapParam(Review.class);
        Customer customer = customerRepo.getReferenceByCuId(shopSession.getId());
        review.setCustomer(customer);

        Map<String, Object> map = ordersMapper.selectOrderAndShopByOggIdx(paramMap.getLong("oggIdx"));
        review.setOrders(ordersRepo.getReferenceById(Long.parseLong(map.get("O_IDX") + "")));
        review.setShopProduct(shopProductRepo.getReferenceById(Long.parseLong(map.get("SP_IDX")+"")));

        List<String> fileList = new ArrayList<>();
        for (MultipartFile mf : mfs) {
            String file = Common.uploadFilePath(mf, "shop_product/review/", AdminBucket.OPEN);
            fileList.add(file);
        }
        review.setRJson(fileList);
        reviewRepo.save(review);
    }
}
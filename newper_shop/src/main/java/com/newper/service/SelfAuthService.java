package com.newper.service;

import com.newper.component.ShopSession;
import com.newper.constant.SaType;
import com.newper.entity.AesEncrypt;
import com.newper.entity.SelfAuth;
import com.newper.repository.SelfAuthRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelfAuthService {

    @Autowired
    private ShopSession shopSession;

    private final SelfAuthRepo selfAuthRepo;
    private final ShopRepo shopRepo;

    /**본인인증 요청 insert*/
    @Transactional
    public Long saveSelfAuth(Map<String, Object> map) {
        SelfAuth sa = SelfAuth.builder()
                .shop(shopRepo.getReferenceById(shopSession.getShopIdx()))
                .saType(SaType.JOIN)
                .saReq(map)
                .saReqDate(LocalDate.now())
                .saReqTime(LocalTime.now())
                .build();
        selfAuthRepo.save(sa);

        return sa.getSaIdx();
    }

    /**본인인증 nice 응답data update*/
    @Transactional
    public void updateSelfAuth(Map<String,Object> map) {
        String saIdx = shopSession.getSaIdx();
        AesEncrypt ae = new AesEncrypt();
        SelfAuth sa = selfAuthRepo.findLockBySaIdx(Long.parseLong(ae.decryptRandom(saIdx)));
        sa.setSaRes(map);
    }
}

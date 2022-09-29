package com.newper.service;

import com.newper.component.ShopSession;
import com.newper.constant.SaCode;
import com.newper.constant.SaType;
import com.newper.entity.SelfAuth;
import com.newper.exception.MsgException;
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
    public Long insertSa(SaType saType) {
        SelfAuth sa = SelfAuth.builder()
                .shop(shopRepo.getReferenceById(shopSession.getShopIdx()))
                .saType(saType)
                .saReqDate(LocalDate.now())
                .saReqTime(LocalTime.now())
                .build();
        selfAuthRepo.save(sa);

        return sa.getSaIdx();
    }

    /**nice 본인인증 요청 data update*/
    @Transactional
    public void updateSaReq(long saIdx, Map<String,Object> niceReq) {
        SelfAuth selfAuth = selfAuthRepo.findById(saIdx).orElseThrow(() -> new MsgException("잘못된 접근입니다."));
        selfAuth.setSaReq(niceReq);
    }

    /**nice 본인인증 응답 data update*/
    @Transactional
    public Long updateSaRes(Map<String,Object> niceRes) {
        long saIdx = Long.parseLong(niceRes.get("REQ_SEQ").toString());
        SelfAuth sa = selfAuthRepo.findById(saIdx).orElseThrow(() -> new MsgException("잘못된 접근입니다"));
        sa.setSaRes(niceRes);
        sa.setSaCode(SaCode.SUCCESS);
        return sa.getSaIdx();
    }
}

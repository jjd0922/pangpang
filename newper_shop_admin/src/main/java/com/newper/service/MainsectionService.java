package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.MainSection;
import com.newper.exception.MsgException;
import com.newper.repository.MainSectionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MainsectionService {
    private final MainSectionRepo mainSectionRepo;

    /** mainsection 순서 변경*/
    @Transactional
    public void mainsectionOrder(long[] msIdxs) {
        for(int i=0; i<msIdxs.length -1; i++){
            MainSection mainSection = mainSectionRepo.getReferenceById(msIdxs[i]);
            mainSection.updateMsOrder(i+1);
        }
    }

    /** 메인섹션 삭제*/
    @Transactional
    public void mainsectionDelete(ParamMap paramMap) {
        MainSection mainSection = mainSectionRepo.findById(paramMap.getLong("msIdx")).orElseThrow(()-> new MsgException("존재하지 않는 메인섹션 입니다."));
        mainSectionRepo.delete(mainSection);
    }

    /** mainsection update*/
    @Transactional
    public void mainsectionUpdate(ParamMap paramMap) {
        MainSection mainSection = mainSectionRepo.findById(paramMap.getLong("msIdx")).orElseThrow(()-> new MsgException("존재하지 않는 메인섹션 입니다."));
        MainSection mainSectionParam = paramMap.mapParam(MainSection.class);

        mainSection.setMsName(mainSectionParam.getMsName());
        mainSection.setMsType(mainSectionParam.getMsType());
        mainSection.setMsOrder(mainSection.getMsOrder()*mainSectionParam.getMsOrder());
        mainSection.setMsJson("test");
    }

    /** mainsection insert*/
    @Transactional
    public Long mainsectionSave(ParamMap paramMap) {
        MainSection mainSection = paramMap.mapParam(MainSection.class);
        mainSection.setMsJson("test");
        int size = mainSectionRepo.findByShop_shopIdx(mainSection.getShop().getShopIdx()).size();
        mainSection.setMsOrder(mainSection.getMsOrder()*(size+1));

        mainSectionRepo.save(mainSection);

        return mainSection.getMsIdx();
    }

    /** mainsection 노출상태 토글 */
    @Transactional
    public void mainsectionDisplayToggle(ParamMap paramMap) {
        MainSection mainSection = mainSectionRepo.findById(paramMap.getLong("msIdx")).orElseThrow(()-> new MsgException("존재하지 않는 메인섹션 입니다."));
        mainSection.setMsOrder(mainSection.getMsOrder() * -1);
    }
}

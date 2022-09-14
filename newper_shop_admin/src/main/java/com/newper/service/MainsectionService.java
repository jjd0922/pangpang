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
    public void mainsectionOrder(List<String> msIdxs) {
        for(int i=0; i<msIdxs.size() -1; i++){
            MainSection mainSection = mainSectionRepo.findById(Long.parseLong(msIdxs.get(i))).orElseThrow(()->new MsgException("존재하지 않는 메인섹션 입니다."));
            mainSection.updateMainsectionOrder(i+1);
        }
    }

    @Transactional
    public void mainsectionDelete(ParamMap paramMap) {
        MainSection mainSection = mainSectionRepo.findById(paramMap.getLong("msIdx")).orElseThrow(()-> new MsgException("존재하지 않는 메인섹션 입니다."));
        mainSectionRepo.delete(mainSection);
    }

    /** mainsection update*/
    public void mainsectionUpdate(ParamMap paramMap) {
        MainSection mainSection = mainSectionRepo.findById(paramMap.getLong("msIdx")).orElseThrow(()-> new MsgException("존재하지 않는 메인섹션 입니다."));
        MainSection mainSectionParam = paramMap.mapParam(MainSection.class);

        mainSection.setMsName(mainSectionParam.getMsName());
        mainSection.setMsType(mainSectionParam.getMsType());
        mainSection.setMsOrder(mainSection.getMsOrder()*mainSectionParam.getMsOrder());
        mainSection.setMsColumn(mainSectionParam.getMsColumn());

    }

    /** mainsection insert*/
    @Transactional
    public void mainsectionSave(ParamMap paramMap) {
        MainSection mainSection = paramMap.mapParam(MainSection.class);

        mainSectionRepo.save(mainSection);
        mainSection.setMsOrder(mainSection.getShop().getMainSections().size()*mainSection.getMsOrder());
    }
}

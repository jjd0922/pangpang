package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.CheckGroup;
import com.newper.repository.CheckGroupRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CheckService {

    private final CheckGroupRepo checkGroupRepo;

    /** 검수 그룹 등록*/
    @Transactional
    public void insertCheckGroup(ParamMap paramMap){
        CheckGroup checkGroup = paramMap.mapParam(CheckGroup.class);

        long ggt_idx = paramMap.getLong("ggt_idx");

        checkGroupRepo.save(checkGroup);


        //check goods



        // delete ggt_idx

    }
}

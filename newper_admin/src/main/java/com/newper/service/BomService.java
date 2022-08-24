package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.mapper.BomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BomService {

    private final BomMapper bomMapper;

    @Transactional
    public void saveBom(ParamMap paramMap) {
        bomMapper.insertBom(paramMap.getMap());
    }

    @Transactional
    public void updateBom(ParamMap paramMap) {
        bomMapper.deleteBom(paramMap.getMap());
        bomMapper.insertBom(paramMap.getMap());
    }
}

package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.exception.MsgException;
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
        // 구성항목 null체크
        if (paramMap.getList("cpIdx").size() <= 0) {
            throw new MsgException("BOM구성항목을 입력해주세요.");
        }
        
        // 모상품이 이미 bom에서 모상품으로 등록되어 있는지 확인
        int cnt = bomMapper.countBom(paramMap.getMap());
        if (cnt > 0) {
            throw new MsgException("해당 상품의 BOM이 이미 존재합니다.");
        }
        
        // BOM 등록
        bomMapper.insertBom(paramMap.getMap());
    }

    @Transactional
    public void updateBom(ParamMap paramMap) {
        if (paramMap.getList("cpIdx").size() <= 0) {
            throw new MsgException("BOM구성항목을 입력해주세요.");
        }
        bomMapper.deleteBom(paramMap.getMap());
        bomMapper.insertBom(paramMap.getMap());
    }
}

package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.HeaderMenu;
import com.newper.exception.MsgException;
import com.newper.mapper.HeaderMenuMapper;
import com.newper.repository.HeaderMenuRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HeaderMenuService {

    private final HeaderMenuRepo headerMenuRepo;
    private final HeaderMenuMapper headerMenuMapper;

    /** GNB메뉴 순서 변경*/
    @Transactional
    public void headerMenuOrder(Integer[] hmIdxs) {

        for(int i=0; i<hmIdxs.length -1; i++){
            HeaderMenu headerMenu = headerMenuRepo.getReferenceById(hmIdxs[i]);
            headerMenu.updateHmOrder(i+1);
        }
    }

    /** GNB 메뉴 노출/미노출 토글 */
    @Transactional
    public void headerMenuDisplayToggle(ParamMap paramMap) {
        HeaderMenu headerMenu = headerMenuRepo.findById(paramMap.getInt("hmIdx")).orElseThrow(()-> new MsgException("존재하지 않는 GNB 메뉴 입니다."));
        headerMenu.setHmOrder((byte) (headerMenu.getHmOrder() * -1));
    }
    /** GNB 메뉴 delete*/
    @Transactional
    public String headerMenuDelete(ParamMap paramMap) {
        HeaderMenu headerMenu = headerMenuRepo.findById(paramMap.getInt("hmIdx")).orElseThrow(()-> new MsgException("존재하지 않는 GNB 메뉴 입니다."));
        if(headerMenu.isHmDefault()){
            return "삭제할 수 없는 GNB 메뉴입니다.";
        }else{
            headerMenuRepo.delete(headerMenu);
            return "삭제 완료";
        }
    }

    /** GNB 메뉴 update*/
    @Transactional
    public void headerMenuUpdate(ParamMap paramMap) {
        HeaderMenu headerMenu = headerMenuRepo.findById(paramMap.getInt("hmIdx")).orElseThrow(()-> new MsgException("존재하지 않는 GNB 메뉴 입니다."));
        HeaderMenu headerMenuParam = paramMap.mapParam(HeaderMenu.class);
        paramMap.printEntrySet();
        System.out.println("ddd ");
        System.out.println(headerMenuParam.getHmUrl());

        headerMenu.setHmOrder((byte) (headerMenu.getHmOrder() * headerMenuParam.getHmOrder()));
        headerMenu.setHmClass(headerMenuParam.getHmClass());
        headerMenu.setHmName(headerMenuParam.getHmName());
        headerMenu.setHmUrl(headerMenuParam.getHmUrl());

    }
    /** GNB 메뉴 insert*/
    @Transactional
    public Integer headerMenuSave(ParamMap paramMap) {
        Integer hmCount = headerMenuMapper.countMainMenu(paramMap.getInt("shopIdx"));
        HeaderMenu headerMenu = paramMap.mapParam(HeaderMenu.class);

        headerMenu.setHmDefault(false);
        headerMenu.setHmOrder((byte) (headerMenu.getHmOrder() * (hmCount+1)));

        headerMenuRepo.save(headerMenu);
        return headerMenu.getHmIdx();
    }
}
